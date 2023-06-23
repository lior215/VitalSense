package com.lior215.vitalsense.api;

import net.minecraft.client.Minecraft;

import static com.lior215.vitalsense.client.AirQualityOverlay.indicatorX;

public class AirQuality {
    static int defaultIndicatorPos = Minecraft.getInstance().screen.width / 2 - 94;
    static int defaultMaxQuality = 180;

    public static void setAirQuality(int value) {
        indicatorX = value + defaultIndicatorPos;
    }

    public static int getAirQuality() {
        return indicatorX - defaultIndicatorPos;
    }

    public static int getMaxAirQuality() {
        return defaultMaxQuality;
    }
}
