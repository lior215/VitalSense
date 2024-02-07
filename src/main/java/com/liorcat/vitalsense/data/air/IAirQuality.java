package com.liorcat.vitalsense.data.air;

public interface IAirQuality {
    boolean getActive();
    float getQuality();
    float getMaxQuality();

    void setActive(boolean active);
    void setQuality(float health);
}
