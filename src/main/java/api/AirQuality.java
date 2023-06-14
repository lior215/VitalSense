package api;

import net.minecraft.client.Minecraft;

import static com.lior215.vitalsense.client.AirQualityOverlay.indicatorX;
import static com.lior215.vitalsense.vitalsense.LOGGER;

public class AirQuality {
    public static int defaultIndicatorPos = Minecraft.getInstance().screen.width/2 - 94;
    public static void setAirQuality(int value) {
        indicatorX = value + defaultIndicatorPos;
        LOGGER.info(String.valueOf(indicatorX));
        LOGGER.info(String.valueOf(value));
    }

    public static int getAirQuality() {
        return indicatorX - defaultIndicatorPos;
    }
}