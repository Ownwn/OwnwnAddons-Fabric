package com.ownwn;

import com.ownwn.config.Config;
import com.ownwn.features.CustomName;
import com.ownwn.features.ReplaceTooltip;
import com.ownwn.utils.TextUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import meteordevelopment.orbit.EventBus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
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

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> ConfigCommand.register(dispatcher) );
        AutoConfig.register(Config.class, GsonConfigSerializer::new);


        EVENT_BUS.registerLambdaFactory("com.ownwn", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        EVENT_BUS.subscribe(ConfigCommand.class);
        EVENT_BUS.subscribe(CustomName.class);
        EVENT_BUS.subscribe(ReplaceTooltip.class);
    }

}
