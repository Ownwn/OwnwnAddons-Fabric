package com.ownwn.utils;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ownwn.OwnwnAddons;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TextUtils {
    public static Text modifyText(Text originalText, Text targetText, Text replacementText) {

        // text.contains(otherText) doesn't work for some reason
        if (!originalText.getString().contains(targetText.getString())) {
            return originalText;
        }

        /*
        Text is kinda weird. it can have both "content", which is the body of the text, and "siblings" which are other
        pieces of text appended to the end of the content. The siblings can also have their own siblings and so on, so
        this method should probably be made to work recursively eventually, but for now two layers should suffice.

        e.g.
        Text: {
            content,
            sibling: {
                content,
                sibling: {
                    ...
                }
            }
        }

        I nearly went insane making this
         */

        MutableText newMessage = Text.empty();

        // Create a list for all Text instances to be processed.
        List<Text> processingList = new ArrayList<>();

        // add the content of the text to the processing list
        MutableText originalContent = getText(originalText.getContent()).copy();
        processingList.add(originalContent.setStyle(originalText.getStyle()));

        // nestedSibling = second layer sibling
        for (Text nestedSibling : originalText.getSiblings()) {
            // append nested sibling content
            MutableText nestedSiblingContent = getText(nestedSibling.getContent()).copy();
            processingList.add(nestedSiblingContent.setStyle(nestedSibling.getStyle()));

            // hopefully these siblings don't have another layer of siblings
            processingList.addAll(nestedSibling.getSiblings());

        }

        for (Text textToProcess : processingList) {

            String originalString = textToProcess.getString();
            String replacementString = targetText.getString();

            Style originalStyle = textToProcess.getStyle(); // get original style to preserve colours

            if (!originalString.contains(replacementString)) {
                newMessage.append(textToProcess);
                continue;
            }

            String[] parts = originalString.split(replacementString, -1); // include trailing empty strings

            for (int i = 0; i < parts.length - 1; i++) { // loop until second to last
                newMessage.append(Text.literal(parts[i]).setStyle(originalStyle));
                newMessage.append(replacementText);
            }
            // Append the last part outside the loop
            newMessage.append(Text.literal(parts[parts.length - 1]).setStyle(originalStyle));

        }

        if (newMessage.getString().equals("")) {
            return originalText;
        }

        return newMessage;
    }


    private static Text getText(TextContent content) {
        try {
            // TextContent parsing is weird
            return content.parse(null, null, 0);
        } catch (CommandSyntaxException e) {
            OwnwnAddons.LOGGER.info("Something went REALLY wrong while parsing text.", e);
            throw new RuntimeException();
        }
    }

    public static Text dynamicTextSpectrum(Text inputText) {

        // the time in nanoseconds to cycle the full colour spectrum
        long cycleTime = 2_000_000_000L;

        // between 0 and 1, determines the hue
        float nanoHue = ((float) (System.nanoTime() % cycleTime) / cycleTime);

        Color color = Color.getHSBColor(nanoHue, 1f, 1f);

        Style originalStyle = inputText.getStyle();
        return inputText.copy().setStyle(originalStyle.withColor(color.getRGB()));
    }

    public static Text dynamicTextSpectrum(String string) {
        return dynamicTextSpectrum(Text.literal(string));
    }


    public static Text solidTextSpectrum(Text inputText) {
        MutableText editorText = Text.empty().copy();
        int numColorGroups = 10; // number of color groups
        int hueRange = 360 / numColorGroups; // range for each color group

        Random random = new Random();
        int colorGroupIndex = random.nextInt(numColorGroups); // randomly choose a color group

        int hueStart = (int) colorGroupIndex * hueRange; // starting hue value based on color group
        int hueEnd = ((int) colorGroupIndex + 1) * hueRange; // ending hue value based on color group

        String replacementTextString = inputText.getString();
        int length = replacementTextString.length();

        for (int i = 0; i < length; i++) {
            float progress = (float) i / (length - 1); // calculate progress along gradient

            // interpolate hue between start and end
            int hue = (int) (hueStart + (progress * (hueEnd - hueStart)));

            // convert hsl to rgb
            Color color = Color.getHSBColor(hue / 360f, 1f, 1f);

            char character = replacementTextString.charAt(i);

            editorText.append(Text.literal(String.valueOf(character)).setStyle(Style.EMPTY.withColor(color.getRGB())));
        }
        return editorText;
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
