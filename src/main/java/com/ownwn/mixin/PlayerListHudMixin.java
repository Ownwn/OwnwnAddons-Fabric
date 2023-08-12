package com.ownwn.mixin;


import com.ownwn.feature.CustomName;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Comparator;
import java.util.List;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Shadow
    private static Comparator<PlayerListEntry> ENTRY_ORDERING;

    /**
     * @author good old intellij
     * @reason warnings
     */
    @Overwrite
    private List<PlayerListEntry> collectPlayerEntries() {
        assert MinecraftClient.getInstance().player != null;
        List<PlayerListEntry> originalList = new java.util.ArrayList<>(MinecraftClient.getInstance().player.networkHandler.getListedPlayerListEntries().stream().sorted(ENTRY_ORDERING).limit(80L).toList());

        return CustomName.editPlayerList(originalList);

    }
}
