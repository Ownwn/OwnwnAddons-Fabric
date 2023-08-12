package com.ownwn.event;

import com.ownwn.OwnwnAddons;
import com.ownwn.command.MainCommand;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class FabricEventHandler {
    public static void registerEvents() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> MainCommand.register(dispatcher) );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().player == null) {
                return;
            }
            OwnwnAddons.EVENT_BUS.post(new ClientTickEvent());
        });
    }
}
