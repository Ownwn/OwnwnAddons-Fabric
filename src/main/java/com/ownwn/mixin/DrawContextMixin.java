package com.ownwn.mixin;

import com.ownwn.OwnwnAddons;
import com.ownwn.event.DrawTooltipEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(DrawContext.class)
public class DrawContextMixin {
    @ModifyVariable(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V", at = @At("HEAD"), argsOnly = true)
    public List<Text> drawTooltip(List<Text> text) {
        return OwnwnAddons.EVENT_BUS.post(new DrawTooltipEvent(text)).getText();
    }
}
