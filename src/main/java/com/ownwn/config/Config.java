package com.ownwn.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@me.shedaniel.autoconfig.annotation.Config(name = "ownwnaddons")
public class Config implements ConfigData {
    public boolean hideEmptyMessages = false;
    public boolean hideBloodMessages = false;
    public boolean dungeonChatCleanup = false;
    public boolean hideUselessAbilities = false;
    public boolean hideBlessingMessages = false;
    public boolean hideUselessOruo = false;
    public boolean hideMortMessages = false;
    public boolean hideTrapMessages = false;
    public boolean bazaarChatCleanup = false;
    public boolean doNotCloseChat = false;
    public boolean hideAbilityDamage = false;
    public boolean hideGexpMessages = false;

    public int instaSellLimit = 0;
    public int instaBuyLimit = 0;

    public boolean customNameToggle = false;
    public String customNameString = "custom name";

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public NameType nameType = NameType.COLOUR_SPECTRUM;
    public enum NameType {
        SOLID_COLOUR, COLOUR_SPECTRUM
    }

    public int customNameColour = 65280;


    public static Config get() {
        return AutoConfig.getConfigHolder(Config.class).getConfig();
    }

}