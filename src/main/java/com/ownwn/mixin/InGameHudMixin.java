package com.ownwn.mixin;

import com.ownwn.feature.SidebarChanger;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
//        OwnwnAddons.EVENT_BUS.post(new RenderGameOverlayEvent(context));
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("RETURN"))
    private void onRenderSidebar(DrawContext context, ScoreboardObjective objective, CallbackInfo ci) {
        SidebarChanger.stuff(context, objective);

    }
}
