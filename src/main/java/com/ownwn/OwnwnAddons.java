package com.ownwn;

import com.ownwn.command.MainCommand;
import com.ownwn.config.Config;
import com.ownwn.event.FabricEventHandler;
import com.ownwn.feature.CustomName;
import com.ownwn.feature.DevTesting;
import com.ownwn.feature.chat.MoneyChatCleanup;
import com.ownwn.feature.chat.ChatFilter;
import com.ownwn.feature.chat.DungeonChatFilter;
import com.ownwn.util.TextUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import meteordevelopment.orbit.EventBus;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class OwnwnAddons implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ownwnaddons");

    public static Text Prefix() {
        return TextUtils.dynamicTextSpectrum("<OWA>");
    }

    public static EventBus EVENT_BUS = new EventBus();

    @Override
    public void onInitialize() {

        FabricEventHandler.registerEvents();
        AutoConfig.register(Config.class, GsonConfigSerializer::new);

        EVENT_BUS.registerLambdaFactory("com.ownwn", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        EVENT_BUS.subscribe(MainCommand.class);
        EVENT_BUS.subscribe(CustomName.class);
        EVENT_BUS.subscribe(MoneyChatCleanup.class);
        EVENT_BUS.subscribe(ChatFilter.class);
        EVENT_BUS.subscribe(DevTesting.class);
        EVENT_BUS.subscribe(DungeonChatFilter.class);
    }

}
