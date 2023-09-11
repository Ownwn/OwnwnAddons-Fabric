package com.ownwn.event;

import com.ownwn.OwnwnAddons;
import com.ownwn.command.MainCommand;
import com.ownwn.feature.WaterSolver;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public class FabricEventHandler {
    private static int tickCount = 0;
    public static void registerEvents() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> MainCommand.register(dispatcher) );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().player == null) {
                return;
            }
            OwnwnAddons.EVENT_BUS.post(new ClientTickEvent.TickClassic());

            tickCount++;
            if (tickCount >= 20) {
                tickCount = 0;
                OwnwnAddons.EVENT_BUS.post(new ClientTickEvent.TickSecond());
            }
        });

        WorldRenderEvents.END.register(WaterSolver::onRender);


    }
}
