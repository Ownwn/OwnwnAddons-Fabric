package com.ownwn.config;

import com.google.gson.*;
import com.ownwn.OwnwnAddons;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PersistentConfig {

    public static String configPath = "";

    private static JsonObject createOrUpdateJsonObject(String key, JsonElement value) {
        JsonObject jsonObject;

        // Create new gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Check if file is empty
        File file = new File(configPath);
        if (file.length() == 0) {
            jsonObject = new JsonObject(); // Create a new JsonObject if file is empty
        } else {
            // If file isn't empty, read its contents
            String jsonString;
            try {
                jsonString = new String(Files.readAllBytes(Paths.get(configPath)));
            } catch (IOException e) {
                corruptConfigWarn(e);
                return null;
            }

            // parse file contents into a JsonObject
            jsonObject = gson.fromJson(jsonString, JsonObject.class);
        }

        // add/update key-value pair in the JsonObject
        jsonObject.add(key, value);

        return jsonObject;
    }

    private static void writeJsonObjectToFile(JsonObject jsonObject) {
        // Write modified jsonObject back into the config file
        try (FileWriter fileWriter = new FileWriter(configPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            OwnwnAddons.LOGGER.error("Failed to write to config file!", e);
        }
    }

    public static void checkConfigFile() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir() + "\\ownwnaddons.json");
        configPath = configFile.getPath();

        if (!configFile.exists()) {
            try {
                boolean isCreated = configFile.createNewFile();
                if (isCreated) {
                    OwnwnAddons.LOGGER.info("Successfully created config file!");
                } else {
                    OwnwnAddons.LOGGER.error("Failed to create config file.");
                }
            } catch (IOException e) {
                OwnwnAddons.LOGGER.error("Failed to create config file.", e);
            }

        }
        NewConfig.registerConfig();
        // call this after to ensure the config file path is set correctly
    }

    public static JsonElement readJsonElement(String key) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(configPath));
        } catch (FileNotFoundException e) {
            OwnwnAddons.LOGGER.error("Failed to read from config file!");
            e.printStackTrace();
            return null;
        }

        JsonObject jsonObject = JsonParser.parseReader(br).getAsJsonObject();
        return jsonObject.get(key);
    }

    public static boolean readJsonBoolean(String key) {
        return readJsonElement(key).getAsBoolean();
    }

    public static boolean validateJson(String key) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(configPath));
            JsonElement jsonElement = JsonParser.parseReader(br);

            // Check if the jsonElement is not null and it's an object
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return jsonObject.has(key) && jsonObject.get(key).isJsonPrimitive();
            }

        } catch (FileNotFoundException | JsonSyntaxException e) {
            corruptConfigWarn(e);
        }

        return false;
    }

    public static void writeJson(String key, String value) {
        JsonObject jsonObject = createOrUpdateJsonObject(key, new JsonPrimitive(value));
        writeJsonObjectToFile(jsonObject);
    }

    public static void writeJson(String key, boolean value) {
        JsonObject jsonObject = createOrUpdateJsonObject(key, new JsonPrimitive(value));
        writeJsonObjectToFile(jsonObject);
    }

    public static void writeDefault(String key, String value) {
        JsonElement jsonElement = readJsonElement(key);
        // Only write the default value if the key does not exist
        if (jsonElement == null || jsonElement.isJsonNull()) {
            writeJson(key, value);
        }
    }

    public static void corruptConfigWarn(Exception e) {
        OwnwnAddons.LOGGER.error("Failed to read from config file. Config may be corrupted," +
                " try deleting the file at " + configPath, e);
        throw new RuntimeException();
    }
}
