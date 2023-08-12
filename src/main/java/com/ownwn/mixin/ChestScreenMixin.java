package com.ownwn.mixin;


import com.ownwn.feature.CancelBazaarClicks;
import com.ownwn.util.Chat;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HandledScreen.class, priority = 1100)
public class ChestScreenMixin {

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    public void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CancelBazaarClicks.shouldCancelClick(slot)) {
            Chat.prefixInfo("Stopped you from insta-selling your items! Disable this in /owa");
            ci.cancel();
        }
    }


}
