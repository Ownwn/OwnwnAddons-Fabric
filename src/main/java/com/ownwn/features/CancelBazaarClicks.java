package com.ownwn.features;

import com.ownwn.config.Config;
import com.ownwn.utils.NbtUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CancelBazaarClicks {
    public static boolean shouldCancelClick(Slot slot) {
        // TODO: finish this
        if (!Config.get().preventSellingKismets) {
            return false;
        }
        if (slot == null || slot.getStack() == null) {
            return false;
        }
        ItemStack item = slot.getStack();
        if (!item.getName().getString().contains("Sell Inventory Now")) {
            return false;
        }
        if (!item.hasNbt() || item.getNbt() == null) {
            return false;
        }
        return NbtUtils.checkMatch(item.getNbt(), "asda");
    }
}
