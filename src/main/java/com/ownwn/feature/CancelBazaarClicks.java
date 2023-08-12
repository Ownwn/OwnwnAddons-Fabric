package com.ownwn.feature;

import com.ownwn.config.Config;
import com.ownwn.util.NbtUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CancelBazaarClicks {
    public static String[] dungeonItems = {
            "Kismet Feather",
            "Superboom TNT",
            "Spirit Leap"
    };

    public static boolean shouldCancelClick(Slot slot) {
        if (!Config.get().preventSellingDungeons && !Config.get().preventSellingPearls) {
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

        if (Config.get().preventSellingPearls && NbtUtils.checkNameMatch(item.getNbt(), "x Ender Pearl for ")) {
            return true;
        }
        return Config.get().preventSellingDungeons && NbtUtils.checkBazaarMatch(item.getNbt(), dungeonItems);
    }
}
