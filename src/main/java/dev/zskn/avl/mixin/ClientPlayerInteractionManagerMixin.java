package dev.zskn.avl.mixin;

import dev.zskn.avl.config.AVLConfig;
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

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private boolean breakingBlock;

    @Inject(method = "attackBlock", at = @At(value = "HEAD"), cancellable = true)
    void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (isBlacklistedAndFeatureEnabled(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At(value = "HEAD"), cancellable = true)
    void onUpdateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (isBlacklistedAndFeatureEnabled(pos)) {
            this.breakingBlock = false;
            cir.setReturnValue(false);
        }
    }

    private boolean isBlacklistedAndFeatureEnabled(BlockPos pos) {
        if (!AVLConfig.tweakBreakBlockToggle) {
            return false;
        }

        assert this.client.world != null;
        Block block = this.client.world.getBlockState(pos).getBlock();
        return AVLConfig.tweakBreakBlockBlacklist.contains(block);
    }
}
