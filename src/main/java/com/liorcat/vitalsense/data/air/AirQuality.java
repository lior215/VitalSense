package com.liorcat.vitalsense.data.air;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class AirQuality implements INBTSerializable<CompoundTag> {
    private boolean active;
    private float quality;

    public AirQuality(boolean active, float quality) {
        this.active = active;
        this.quality = quality;
    }

    public AirQuality() {
        this(true, 0);
    }

    public boolean isActive() {
        return active;
    }

    public float getQuality() {
        return quality;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("active", isActive());
        tag.putFloat("quality", getQuality());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setActive(nbt.getBoolean("active"));
        setQuality(nbt.getFloat("quality"));
    }
}

