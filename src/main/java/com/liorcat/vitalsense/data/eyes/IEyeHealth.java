package com.liorcat.vitalsense.data.eyes;

public interface IEyeHealth {
    boolean getActive();
    float getHealth();
    float getMinHealth();
    float getMaxHealth();

    void setActive(boolean active);
    void setHealth(float health);
    void setMinHealth(float minHealth);
    void setMaxHealth(float maxHealth);
}
