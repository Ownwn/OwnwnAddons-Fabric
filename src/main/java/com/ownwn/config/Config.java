package com.ownwn.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@me.shedaniel.autoconfig.annotation.Config(name = "ownwnaddons")
public class Config implements ConfigData {
    public boolean hideEmptyMessages = false;
    public boolean dungeonChatCleanup = false;
    public boolean bazaarChatCleanup = false;
    public boolean doNotCloseChat = false;

    public boolean preventSellingDungeons = false;
    public boolean preventSellingPearls = false;

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