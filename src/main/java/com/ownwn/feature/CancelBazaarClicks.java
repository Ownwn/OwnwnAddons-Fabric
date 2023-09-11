package com.ownwn.feature;

import com.ownwn.config.Config;
import com.ownwn.event.HandledScreenClickEvent;
import com.ownwn.util.Chat;
import com.ownwn.util.NbtUtils;
import com.ownwn.util.Utils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CancelBazaarClicks {
    private static final String[] instaBuyNames = {
            "Buy only one!",
            "Buy a stack!",
            "Fill my inventory!",
            "Custom Amount"
    };

    @EventHandler
    public static void shouldCancelClick(HandledScreenClickEvent event) {
        if (Config.get().instaBuyLimit == 0 && Config.get().instaSellLimit == 0) return;

        Slot slot = event.getSlot();
        if (slot == null) return;

        ItemStack item = slot.getStack();
        if (item == null || !item.hasNbt()) return;

        String itemName = Utils.removeLegacyFormatting(item.getName().getString());

        if (Config.get().instaSellLimit != 0 && shouldCancelSell(item, itemName)) {
            Chat.prefixInfo("Stopped you insta-selling over your sell limit! Disable this in /owa");
            event.cancel();
            return;
        }

        if (Config.get().instaBuyLimit != 0 && shouldCancelBuy(item, itemName)) {
            Chat.prefixInfo("Stopped you over-spending whilst insta-buying! Disable this in /owa");
            event.cancel();
            return;
        }
    }

    private static boolean shouldCancelSell(ItemStack item, String itemName) {
        if (!itemName.equals("Sell Inventory Now") && !itemName.equals("Sell Instantly")) return false;

        String targetLore = itemName.equals("Sell Inventory Now") ? "You earn: " : "Total: ";

        for (String line : NbtUtils.getItemLoreList(item, true)) {
            if (line.startsWith(targetLore) && line.endsWith(" coins")) {
                double sellPrice = parsePrice(line, targetLore);
                if (sellPrice > Config.get().instaSellLimit) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean shouldCancelBuy(ItemStack item, String itemName) {
        for (String instaBuyWord : instaBuyNames) {
            if (!itemName.equals(instaBuyWord)) continue;

            for (String line : NbtUtils.getItemLoreList(item, true)) {
                if (line.startsWith("Price: ") && line.endsWith(" coins")) {
                    double buyPrice = parsePrice(line, "Price: ");
                    if (buyPrice > Config.get().instaBuyLimit) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static double parsePrice(String line, String prefix) {
        String priceString = line.replace(prefix, "").replace(" coins", "").replace(",", "");
        try {
            return Double.parseDouble(priceString);
        } catch (NumberFormatException exception) {
            Chat.prefixError("Error parsing bazaar item price. Check the logs for more info.");
            return -1;
        }
    }
}
