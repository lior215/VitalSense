package com.lior215.vitalsense.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import static com.lior215.vitalsense.client.AirQualityOverlay.indicatorX;

@AutoRegisterCapability
public class AirQuality {
    int defaultIndicatorPos = Minecraft.getInstance().screen.width / 2 - 94;
    int defaultMaxQuality = 180;

    public void setAirQuality(int value) {
        indicatorX = value + this.defaultIndicatorPos;
    }

    public int getAirQuality() {
        return indicatorX - this.defaultIndicatorPos;
    }

    public int getMaxAirQuality() {
        return this.defaultMaxQuality;
    }
}
