package com.ownwn.config;

import com.ownwn.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Text.of("OwnwnAddons Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {

        int buttonWidth = 140;
        int buttonHeight = 20;
        int buttonGap = 5;
        int totalButtonHeight = (buttonHeight + buttonGap) * 12 - buttonGap; // Total height of all buttons including gaps
        int startX = (this.width - buttonWidth) / 2; // X-coordinate for the buttons
        int startY = (this.height - totalButtonHeight) / 2; // Y-coordinate for the buttons

        int i = 0;
        for (ConfigOption option : ConfigOption.configList) {
            // iterate over list of config options and draw them seperated by a gap
            String key = option.getKey();
            boolean value = option.getValue();

            this.addDrawableChild(regularButton(startX, startY +(buttonHeight + buttonGap) * i, 140, 20, styleBooleanText(key, value, 6941983), button -> {
                option.setValue(!option.getValue());
                button.setMessage(styleBooleanText(key, option.getValue(), 6941983)); // update the value of the button when clicked
            }));
            i++;
        }
    }



    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
        // go back to the previous screen, e.g. mod menu page
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredTextWithShadow(matrices, textRenderer, title, width / 2, 30, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }


    public static Text styleBooleanText(String text, boolean value, int color) {
        return Text.literal(text + ": " + Utils.capitalString(value)).setStyle(Style.EMPTY.withColor(color));
    }



    public static ButtonWidget regularButton(int x, int y, Text text, ButtonWidget.PressAction pressAction) {

        ButtonWidget.Builder buttonBuilder = new ButtonWidget.Builder(text, pressAction);
        buttonBuilder.size(100, 20);
        buttonBuilder.position(x, y);
        return buttonBuilder.build();
    }

    public static ButtonWidget regularButton(int x, int y, String text, int color, ButtonWidget.PressAction pressAction) {

        ButtonWidget.Builder buttonBuilder = new ButtonWidget.Builder(Text.literal(text).setStyle(Style.EMPTY.withColor(color)), pressAction);
        buttonBuilder.size(100, 20);
        buttonBuilder.position(x, y);
        return buttonBuilder.build();
    }

    public static ButtonWidget regularButton(int x, int y, int width, int length, Text text, ButtonWidget.PressAction pressAction) {

        ButtonWidget.Builder buttonBuilder = new ButtonWidget.Builder(text, pressAction);
        buttonBuilder.size(width, length);
        buttonBuilder.position(x, y);
        return buttonBuilder.build();
    }
}