package com.ownwn;

import com.ownwn.config.NewConfig;
import com.ownwn.event.EventBus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwnwnAddons implements ModInitializer {
    public static final String PREFIX = "§d<§aOWA§d>§r ";
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("ownwnaddons");


    public static final EventBus EVENT_BUS = new EventBus();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        NewConfig.registerConfig();
        EVENT_BUS.register(new ChatMessageListener());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

        });
    }

}
