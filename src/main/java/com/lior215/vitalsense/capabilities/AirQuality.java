package com.lior215.vitalsense.capabilities;

import com.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import static com.lior215.vitalsense.client.AirQualityOverlay.indicatorX;
import static com.lior215.vitalsense.vitalsense.LOGGER;

@AutoRegisterCapability
public class AirQuality {
    int defaultIndicatorPos = Minecraft.getInstance().screen.width / 2 - 94;
    int defaultMaxQuality = 180;

    int airQuality = indicatorX;

    public void setAirQuality(int value) {
        indicatorX = value + this.defaultIndicatorPos;
    }

    public int getAirQuality() {
        return indicatorX - this.defaultIndicatorPos;
    }

    public int getMaxAirQuality() {
        return this.defaultMaxQuality;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("air_quality", airQuality);
    }


    public void loadNBTData(CompoundTag nbt) {
        LOGGER.info("loaded nbt data for air quality");
        this.airQuality = nbt.getInt("air_quality");
    }

}