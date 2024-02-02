package com.liorcat.vitalsense.data.eyes;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class EyeHealth implements INBTSerializable<CompoundTag> {
    private boolean active;
    private float health;
    private float maxHealth;
    private float minHealth;

    public EyeHealth(boolean active, float health, float minHealth, float maxHealth) {
        this.active = active;
        this.health = health;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
    }

    public EyeHealth() {
        this(true, 100, 100, 0);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getMinHealth() {
        return minHealth;
    }

    public void setMinHealth(float minHealth) {
        this.minHealth = minHealth;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("active", isActive());
        tag.putFloat("health", getHealth());
        tag.putFloat("minHealth", getMinHealth());
        tag.putFloat("maxHealth", getMaxHealth());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setActive(nbt.getBoolean("active"));
        setHealth(nbt.getFloat("health"));
        setMinHealth(nbt.getFloat("minHealth"));
        setMaxHealth(nbt.getFloat("maxHealth"));
    }
}
