package com.ownwn.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class TextUtils {
    private static Text modifyText(Text originalText, Text targetText, Text replacementText) {

        MutableText newMessage = Text.empty();

        for (Text textSibling : originalText.getSiblings()) {

            String originalString = textSibling.getString();

            String replacementString = targetText.getString();

            Style originalStyle = textSibling.getStyle(); // get original style to preserve colours

            if (originalString.contains(replacementString)) {
                String[] parts = originalString.split(replacementString, -1); // include trailing empty strings

                for (int i = 0; i < parts.length - 1; i++) { // loop until second to last
                    newMessage.append(Text.literal(parts[i]).setStyle(originalStyle));
                    newMessage.append(replacementText);
                }
                // Append the last part outside the loop
                newMessage.append(Text.literal(parts[parts.length - 1]).setStyle(originalStyle));
            } else {
                newMessage.append(textSibling);
            }

        }

        if (newMessage.getString().equals("")) {
            return originalText;
        }

        return newMessage;

    }



    public static Text modifyTextColour(Text message, Text targetText, int newColour) {
        return modifyText(message, targetText, targetText.copy().setStyle(targetText.getStyle().withColor(newColour)));
    }

    public static Text modifyTextColour(String message, String targetText, int newColour) {
        return modifyText(Text.literal(message), Text.literal(targetText), Text.literal(targetText).setStyle(Text.literal(targetText).getStyle().withColor(newColour)));
    }

    public static Text replaceStringText(Text message, String targetText, String replacementText) {
        return modifyText(message, Text.literal(targetText), Text.literal(replacementText));
    }
}
