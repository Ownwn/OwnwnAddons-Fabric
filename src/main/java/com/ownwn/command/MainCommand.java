package com.ownwn.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ownwn.config.Config;
import com.ownwn.feature.DevTesting;
import com.ownwn.feature.WaterSolver;
import com.ownwn.util.Chat;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class MainCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        LiteralCommandNode<FabricClientCommandSource> guiCommand = ClientCommandManager
                .literal("owa")
                .executes(context -> {
                    MinecraftClient.getInstance().send(() -> MinecraftClient.getInstance().setScreen(AutoConfig.getConfigScreen(Config.class, null).get()));
                    return 1;
                })
                .build();


        LiteralCommandNode<FabricClientCommandSource> devMode = ClientCommandManager
                .literal("dev")
                .executes(context -> {
                    DevTesting.devMode = !DevTesting.devMode;
                    Chat.prefixInfo("Dev mode: " + DevTesting.devMode);
                    return 1;
                })
                .build();


        LiteralCommandNode<FabricClientCommandSource> waterMode = ClientCommandManager
                .literal("water")
                .executes(context -> {
                    WaterSolver.water = true;
                    Chat.prefixInfo("done");
                    return 1;
                })
                .build();


        LiteralCommandNode<FabricClientCommandSource> previewCommand = ClientCommandManager
                .literal("preview")
                .build();


        ArgumentCommandNode<FabricClientCommandSource, String> previewCommandString = ClientCommandManager
                .argument("text", StringArgumentType.greedyString())
                .executes(context -> {
                    Chat.chat(StringArgumentType.getString(context, "text").replace("&", "§"));
                    return 1;
                })
                .build();
        previewCommand.addChild(previewCommandString);


        guiCommand.addChild(devMode);
        guiCommand.addChild(waterMode);
        guiCommand.addChild(previewCommand);
        dispatcher.getRoot().addChild(guiCommand);    // register command
    }


}