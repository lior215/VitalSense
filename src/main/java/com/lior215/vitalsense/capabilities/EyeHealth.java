package com.lior215.vitalsense.capabilities;

import com.lior215.vitalsense.client.ClientEyeHealth;
import com.lior215.vitalsense.network.ModPackets;
import com.lior215.vitalsense.network.packets.S2CEyeHealth;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class EyeHealth {

    boolean isActive = true;
    float healthValue = 100;
    float maxHealthValue = 100;
    float minHealthValue = 0;


    public float getHealthValue() {
        return this.healthValue;
    }

    public boolean getIsActive() {
        return isActive;
    }


    public void setHealthValue(float value) {
        this.healthValue = value;
    }

    public void reduceHealthValue(float value) {
        this.healthValue = Math.max(getHealthValue() - value, minHealthValue);
    }

    public void addhealthValue(float value) {
        this.healthValue = Math.min(getHealthValue() + value, maxHealthValue);
    }


    public void copyFrom(EyeHealth source) {
        this.healthValue = source.healthValue;
    }


    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("eyehealth", healthValue);
    }


    public void loadNBTData(CompoundTag nbt) {
        vitalsense.LOGGER.info("loaded nbt data waju");
        this.healthValue = nbt.getFloat("eyehealth");
        ClientEyeHealth.set(nbt.getFloat("eyehealth"));
    }


}
