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
        PersistentConfig.writeJson(this.key, value);
    }

    public static ConfigOption addOption(String key, boolean defaultValue) {
        ConfigOption option;
        // check if already exists in config file, if it does don't overwrite it
        if (!PersistentConfig.validateJson(key)) {
            option = new ConfigOption(key, defaultValue);
        } else {
            option = new ConfigOption(key, PersistentConfig.readJsonBoolean(key));
        }

        configList.add(option);
        return option;
    }
    public static ConfigOption addOption(String key) { // lazy option to make default value false
        return addOption(key, false);
    }


}
