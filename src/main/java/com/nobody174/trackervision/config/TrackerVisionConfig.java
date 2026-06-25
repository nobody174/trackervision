//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.config;

/**
 * In-memory holder for current client settings. Values are clamped on
 * write so a corrupted or hand-edited config file can never produce an
 * invalid runtime state. Persisted by {@link TrackerVisionConfigFile}.
 */
public final class TrackerVisionConfig {

    private static final float MIN_NEAR_DISTANCE = 1.0F;
    private static final float MAX_NEAR_DISTANCE = 32.0F;
    private static final float MIN_FAR_DISTANCE = 8.0F;
    private static final float MAX_FAR_DISTANCE = 256.0F;
    private static final int MIN_BRACKET_SIZE = 8;
    private static final int MAX_BRACKET_SIZE = 64;

    private static boolean trackingEnabled = true;
    private static float nearDistance = 8.0F;
    private static float farDistance = 64.0F;
    private static int bracketBaseSize = 24;
    private static int trackingAccentColor = 0xFF35E0E0;

    private TrackerVisionConfig() {
    }

    public static boolean isTrackingEnabled() {
        return trackingEnabled;
    }

    public static void setTrackingEnabled(boolean value) {
        trackingEnabled = value;
    }

    public static float getNearDistance() {
        return nearDistance;
    }

    public static void setNearDistance(float value) {
        nearDistance = Math.max(MIN_NEAR_DISTANCE, Math.min(MAX_NEAR_DISTANCE, value));
    }

    public static float getFarDistance() {
        return farDistance;
    }

    public static void setFarDistance(float value) {
        farDistance = Math.max(MIN_FAR_DISTANCE, Math.min(MAX_FAR_DISTANCE, value));
    }

    public static int getBracketBaseSize() {
        return bracketBaseSize;
    }

    public static void setBracketBaseSize(int value) {
        bracketBaseSize = Math.max(MIN_BRACKET_SIZE, Math.min(MAX_BRACKET_SIZE, value));
    }

    public static int getTrackingAccentColor() {
        return trackingAccentColor;
    }

    public static void setTrackingAccentColor(int value) {
        trackingAccentColor = 0xFF000000 | (value & 0x00FFFFFF);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
