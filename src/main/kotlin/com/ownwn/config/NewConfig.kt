package com.ownwn.config

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.autogen.*
import dev.isxander.yacl3.config.v2.api.autogen.Boolean
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.Identifier
import java.awt.Color
import java.nio.file.Path

class NewConfig {

    companion object {
        private val CONFIG_PATH: Path = FabricLoader.getInstance().configDir.resolve("ownwnaddons.json")

        private val HANDLER: ConfigClassHandler<NewConfig> = ConfigClassHandler.createBuilder(NewConfig::class.java)
            .id(Identifier.of("ownwnaddons", "config"))
            .serializer { config ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_PATH)
                    .build()
            }
            .build()


        fun createScreen(parent: Screen?): Screen {
            return HANDLER.generateGui().generateScreen(parent)
        }

        fun load() {
            HANDLER.load()
        }

        fun save() {
            HANDLER.save()
        }

        fun instance(): NewConfig {
            return HANDLER.instance()
        }
    }


    @SerialEntry
    @AutoGen(category = "custom_name")
    @StringField
    var customName: String = "custom name"

    @SerialEntry
    @AutoGen(category = "custom_name")
    @ColorField
    var nameColour: Color = Color(1000)


    @SerialEntry
    @AutoGen(category = "custom_name")
    @EnumCycler
    var customNameType: CustomNameType = CustomNameType.FANCY_CHROMA

    enum class CustomNameType {
        SOLID_COLOUR,
        PLAIN_CHROMA,
        FANCY_CHROMA
    }


    @SerialEntry
    @AutoGen(category = "chroma")
    @DoubleSlider(min = 0.1, max = 10.0, format = "%.1f", step = 0.1)
    var chromaSpeed: Double = 5.0

    @SerialEntry
    @AutoGen(category = "chroma")
    @DoubleSlider(min = 0.1, max = 10.0, format = "%.1f", step = 0.1)
    var chromaScale: Double = 5.0

    @SerialEntry
    @AutoGen(category = "chroma")
    @FloatSlider(min = 0.1f, max = 1f, step = 0.1f)
    var chromaBrightness: Float = 0.5f

    @SerialEntry
    @AutoGen(category = "chroma")
    @FloatSlider(min = 0.1f, max = 1f, step = 0.1f)
    var chromaSaturation: Float = 0.5f

    @SerialEntry
    @AutoGen(category = "chroma")
    @Boolean(formatter = Boolean.Formatter.ON_OFF)
    var reverseChromaDirection: kotlin.Boolean = false


}