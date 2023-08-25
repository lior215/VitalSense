package com.lior215.vitalsense.api;

import net.minecraft.client.Minecraft;

import static com.lior215.vitalsense.client.AirQualityOverlay.getIndicatorX;

// TODO: 8/25/2023 Remove this once fully switched to Capabilities 

@Deprecated
public class AirQuality {
    static int defaultIndicatorPos;

    static {
        assert Minecraft.getInstance().screen != null;
        defaultIndicatorPos = Minecraft.getInstance().screen.width / 2 - 94;
    }

    static int defaultMaxQuality = 180;

    public static void setAirQuality(int value) {
        getIndicatorX = value + defaultIndicatorPos;
    }

    public static int getAirQuality() {
        return getIndicatorX - defaultIndicatorPos;
    }

    public static int getMaxAirQuality() {
        return defaultMaxQuality;
    }
}
