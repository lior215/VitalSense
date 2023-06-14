package api;

import net.minecraft.client.Minecraft;

import static com.lior215.vitalsense.client.AirQualityOverlay.indicatorX;

public class AirQuality {
    private static final int defaultIndicatorPos = Minecraft.getInstance().screen.width/2 - 94;
    private static final int defaultMax = 140 + defaultIndicatorPos;

    public static void setAirQuality(int value) {
        indicatorX = value + defaultIndicatorPos;
    }

    public static int getAirQuality() {
        return indicatorX - defaultIndicatorPos;
    }

    public static int getMaxAirQuality() {
        return defaultMax;
    }
}