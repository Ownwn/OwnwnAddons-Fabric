package com.ownwn.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.nio.file.Path;


public class NewConfig {
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("ownwnaddons.json");

    // This handler will be used to load and save your mod's configuration.
    private static final ConfigClassHandler<NewConfig> HANDLER = ConfigClassHandler.createBuilder(NewConfig.class)
            .id(new Identifier("ownwnaddons", "config"))

            // You can use various serializes, for the sake of brevity we will use GSON (JSON).
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_PATH)
                    //.setJson5(true) // Uncomment this line to use JSON5 instead of JSON.
                    .build())
            .build();

    public static Screen createScreen(@Nullable Screen parent) {
        // Generate the screen, and return it.
        return HANDLER.generateGui().generateScreen(parent);
    }


    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static NewConfig instance() {
        return HANDLER.instance();
    }


    @SerialEntry
    @AutoGen(category = "custom_name")
    @StringField
    public String customName = "custom name";

    @SerialEntry
//    @CustomName("Custom Name Colour")
//    @CustomDescription("The colour of your custom name")
    @AutoGen(category = "custom_name")
    @ColorField()
    public Color nameColour = new Color(1000);


    @SerialEntry
//    @CustomName("Name Colour Type")
//    @CustomDescription("how your name should look")
    @AutoGen(category = "custom_name")
    @EnumCycler
    public CustomNameType customNameType = CustomNameType.FANCY_CHROMA;

    public enum CustomNameType {
        SOLID_COLOUR,
        PLAIN_CHROMA,
        FANCY_CHROMA
    }

    @SerialEntry
    @AutoGen(category = "chroma")
    @DoubleSlider(min = 0.1, max = 10, format = "%.1f", step = 0.1)
    public double chromaSpeed = 5;

    @SerialEntry
    @AutoGen(category = "chroma")
    @DoubleSlider(min = 0.1, max = 10, format = "%.1f", step = 0.1)
    public double chromaScale = 5;

    @SerialEntry
//    @CustomName("Chroma Brightness")
//    @CustomDescription("Brightness of the chroma")
    @AutoGen(category = "chroma")
    @FloatSlider(min = 0.1f, max = 1, step = 0.1f)
    public float chromaBrightness = 0.5f;

    @SerialEntry
//    @CustomName("Chroma Saturation")
//    @CustomDescription("Saturation of the chroma")
    @AutoGen(category = "chroma")
    @FloatSlider(min = 0.1f, max = 1, step = 0.1f)
    public float chromaSaturation = 0.5f;


}