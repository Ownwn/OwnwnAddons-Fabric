package com.ownwn.config;

import com.ownwn.OwnwnAddons;

public class NewConfig {





    public static final ConfigOption optionFour = ConfigOption.addOption("Option Four", false);

    public static final ConfigOption hideExplosionParticles = ConfigOption.addOption("Hide Implosion Particles");
    public static final ConfigOption modifyPlayerName = ConfigOption.addOption("Modify Player Name", false);

    public static void registerConfig() { // run this to ensure the config options are registered
        OwnwnAddons.LOGGER.info("Registering config...");
    }







}
