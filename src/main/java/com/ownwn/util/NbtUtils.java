package com.ownwn.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NbtUtils {
    public static Pattern textLorePattern = Pattern.compile("\"text\":\"([^\"]*)\"");
    public static final byte STRING_BYTE = 8;


    public static boolean checkNameMatch(NbtCompound nbt, String checkString) {
        // checks if an item's lore contains a string

        if (!nbt.contains("display")) {
            return false;
        }

        // get all lines of lore
        NbtList loreList = nbt.getCompound("display").getList("Lore", STRING_BYTE);

        for (int i = 0; i < loreList.size(); i++) {

            String lore = loreList.getString(i);
            StringBuilder itemName = new StringBuilder();
            Matcher matcher = textLorePattern.matcher(lore);
            while (matcher.find()) {
                itemName.append(matcher.group(1));
            }
            if (!itemName.toString().contains(checkString)) {
                continue;
            }
            return true;

        }
        return false;
    }


    public static List<String> getItemLoreList(ItemStack item, boolean removeFormatting) {
        List<String> loreList = new ArrayList<>();
        NbtCompound nbt = item.getNbt();
        assert nbt != null;
        if (!nbt.contains("display")) {
            return loreList;
        }

        NbtList nbtLore = nbt.getCompound("display").getList("Lore", STRING_BYTE);


        for (int i = 0; i < nbtLore.size(); i++) {

            String lore = nbtLore.getString(i);
            String loreLine = "";
            Matcher matcher = textLorePattern.matcher(lore);
            while (matcher.find()) {
                if (removeFormatting) loreLine = Utils.removeLegacyFormatting(loreLine);
                loreLine += matcher.group(1);
            }
            loreList.add(loreLine.trim());

        }
        return loreList;

    }


    public static boolean checkBazaarMatch(NbtCompound nbt, String[] checkString) {
        for (String itemName : checkString) {
            itemName = "x " + itemName + " for "; // e.g. 5x itemName for 528,038 coins
            if (checkNameMatch(nbt, itemName)) {
                return true;
            }
        }
        return false;
    }
}
