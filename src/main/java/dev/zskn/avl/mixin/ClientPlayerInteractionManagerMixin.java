package dev.zskn.avl.mixin;

import dev.zskn.avl.AVLConfig;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "attackBlock", at = @At(value = "HEAD"), cancellable = true)
    void onUpdateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!AVLConfig.tweakBreakBlockToggle){
            return;
        }

        assert this.client.world != null;
        Block block = this.client.world.getBlockState(pos).getBlock();
        if (block == AVLConfig.blacklisted) {
            cir.setReturnValue(false);
        }
    }
}
