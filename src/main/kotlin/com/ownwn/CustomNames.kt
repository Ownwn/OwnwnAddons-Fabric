package com.ownwn

import com.ownwn.config.NewConfig
import net.minecraft.client.MinecraftClient
import net.minecraft.text.OrderedText
import net.minecraft.text.Text


class CustomNames {
    private val config = NewConfig.instance()

    fun replaceName(text: OrderedText): OrderedText {
        val username = MinecraftClient.getInstance().player?.name?.string ?: return text

        val customName = Text.literal(config.customName)
        if (customName.string.isEmpty()) return text

        val originalTextArray = TextUtils().openOrderedText(text)
        if (!originalTextArray.joinToString("") {it.string}.contains(username)) return text

        val newName = when (config.customNameType) {
            NewConfig.CustomNameType.SOLID_COLOUR -> customName.withColor(config.nameColour.rgb)
            NewConfig.CustomNameType.PLAIN_CHROMA -> customName.withColor(TextUtils().colourSpectrum())
            else -> TextUtils().dynamicSpectrum(customName)
        }

        return TextUtils().replaceOrderedText(text, originalTextArray, username, newName)
    }



}