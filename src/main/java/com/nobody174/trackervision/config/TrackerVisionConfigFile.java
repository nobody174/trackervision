//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads/saves {@link TrackerVisionConfig} to
 * {@code config/trackervision/trackervision-config.json}. Missing or
 * invalid keys fall back to whatever {@link TrackerVisionConfig}'s
 * in-memory defaults already are — a corrupted config file degrades to
 * defaults rather than crashing.
 */
public final class TrackerVisionConfigFile {

    private static final Logger LOGGER = LoggerFactory.getLogger("TrackerVision");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static File configFile;

    private TrackerVisionConfigFile() {
    }

    public static void load() {
        ensureInitialized();
        if (!configFile.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject config = GSON.fromJson(reader, JsonObject.class);
            if (config == null) {
                LOGGER.warn("Config file is empty, keeping defaults");
                return;
            }

            if (config.has("trackingEnabled")) {
                TrackerVisionConfig.setTrackingEnabled(config.get("trackingEnabled").getAsBoolean());
            }
            if (config.has("nearDistance")) {
                TrackerVisionConfig.setNearDistance(config.get("nearDistance").getAsFloat());
            }
            if (config.has("farDistance")) {
                TrackerVisionConfig.setFarDistance(config.get("farDistance").getAsFloat());
            }
            if (config.has("bracketBaseSize")) {
                TrackerVisionConfig.setBracketBaseSize(config.get("bracketBaseSize").getAsInt());
            }
            if (config.has("trackingAccentColor")) {
                try {
                    TrackerVisionConfig.setTrackingAccentColor(
                        Integer.parseInt(config.get("trackingAccentColor").getAsString(), 16));
                } catch (NumberFormatException e) {
                    LOGGER.warn("Invalid trackingAccentColor in config, keeping default", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config, keeping defaults", e);
        }
    }

    public static void save() {
        ensureInitialized();
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject config = new JsonObject();
            config.addProperty("trackingEnabled", TrackerVisionConfig.isTrackingEnabled());
            config.addProperty("nearDistance", TrackerVisionConfig.getNearDistance());
            config.addProperty("farDistance", TrackerVisionConfig.getFarDistance());
            config.addProperty("bracketBaseSize", TrackerVisionConfig.getBracketBaseSize());
            config.addProperty("trackingAccentColor",
                String.format("%06X", TrackerVisionConfig.getTrackingAccentColor() & 0x00FFFFFF));
            GSON.toJson(config, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    private static void ensureInitialized() {
        if (configFile != null) {
            return;
        }
        Path configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("trackervision");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory", e);
        }
        configFile = configDir.resolve("trackervision-config.json").toFile();
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
