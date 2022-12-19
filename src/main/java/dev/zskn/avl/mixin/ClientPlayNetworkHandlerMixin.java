package dev.zskn.avl.mixin;

import dev.zskn.avl.AdvancedVanillaLogistics;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("RETURN"))
    void onOnInventory(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo ci) {
        if (AdvancedVanillaLogistics.config.tweakItemPickupToggle && AdvancedVanillaLogistics.config.tweakItemPickupBlacklist.contains(packet.getItemStack().getItem())) {
            assert this.client.interactionManager != null;
            this.client.interactionManager.clickSlot(packet.getSyncId(), packet.getSlot(), 1, SlotActionType.THROW, this.client.player);
        }
    }
}
