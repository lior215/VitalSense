package com.lior215.vitalsense.capabilities;

import com.lior215.vitalsense.VitalSense;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AirQuality {

    float minHealthValue = 0f; //you might not need it but keep it
    float currentAirQuality = 0f;
    float maxAirQuality = 100f;
    boolean isActive = true;


    public float getCurrentAirQuality() {
        return currentAirQuality;
    }

    public float getMaxAirQuality() {
        return maxAirQuality;
    }

    public void setCurrentAirQuality(float value) {
        this.currentAirQuality = value;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void copyFrom(AirQuality source) {
        this.currentAirQuality = source.currentAirQuality;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("airQuality", currentAirQuality);
    }

    public void loadNBTData(CompoundTag nbt) {
        VitalSense.LOGGER.info("Loaded air quality nbt");
        this.currentAirQuality = nbt.getFloat("airQuality");
    }
}
