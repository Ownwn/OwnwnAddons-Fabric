package com.ownwn.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigOption {

    public static List<ConfigOption> configList = new ArrayList<>(); // register this so it can be looped over when drawing GUI
    private String key;
    private boolean value;

    public ConfigOption(String key, boolean defaultValue) {
        this.key = key;
        this.value = defaultValue;
    }

    public boolean getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public static ConfigOption addOption(String key, boolean defaultValue) {
        ConfigOption option = new ConfigOption(key, defaultValue);
        configList.add(option);
        return option;
    }
    public static ConfigOption addOption(String key) { // lazy option to make default value false
        ConfigOption option = new ConfigOption(key, false);
        configList.add(option);
        return option;
    }
}
