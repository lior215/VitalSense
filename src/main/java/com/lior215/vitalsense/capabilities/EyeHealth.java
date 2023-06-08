package com.lior215.vitalsense.capabilities;

import net.minecraft.nbt.CompoundTag;
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


    public void saveNBTData(CompoundTag nbt) { //crea il data NBT mettendo l'int all'interno
        nbt.putFloat("EyeHealth", healthValue);
    }


    public void loadNBTData(CompoundTag nbt) { //carica il data NBT prendendo l'int all'interno
        nbt.getFloat("EyeHealth");
    }






}
