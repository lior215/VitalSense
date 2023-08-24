package com.lior215.vitalsense.capabilities;

import com.lior215.vitalsense.client.ClientEyeHealth;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import static com.lior215.vitalsense.client.AirQualityOverlay.getIndicatorX;

@AutoRegisterCapability
public class AirQuality {
    int defaultIndicatorPos = (Minecraft.getInstance().screen != null ? Minecraft.getInstance().screen.width : 0) / 2 - 94;
    int defaultMaxQuality = 180;

    int airQuality = getAirQuality();

    int indicatorX = getIndicatorX();

    public int getAirQuality() {
        return this.indicatorX - this.defaultIndicatorPos;
    }
    
    public void setAirQuality(int value) {
        this.indicatorX = value + this.defaultIndicatorPos;
    }

    public int getMaxAirQuality() {
        return this.defaultMaxQuality;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("airQuality", airQuality);
    }


    public void loadNBTData(CompoundTag nbt) {
        vitalsense.LOGGER.info("loaded nbt data for airQuality");
        this.airQuality = nbt.getInt("airQuality");
        ClientEyeHealth.set(nbt.getFloat("airQuality"));
    }
}