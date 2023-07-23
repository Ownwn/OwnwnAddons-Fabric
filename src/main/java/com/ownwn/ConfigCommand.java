package com.ownwn;

import com.mojang.brigadier.CommandDispatcher;
import com.ownwn.config.Config;
import com.ownwn.utils.Chat;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class ConfigCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("owa").executes(context -> {
            MinecraftClient.getInstance().send(() -> MinecraftClient.getInstance().setScreen(AutoConfig.getConfigScreen(Config.class, null).get()));
            return 0;
        }));

        dispatcher.register(ClientCommandManager.literal("test").executes(context -> {
            Chat.prefixInfo("Test command!");
            return 0;
        }));
    }
}