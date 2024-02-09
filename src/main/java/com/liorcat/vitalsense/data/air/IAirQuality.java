package com.liorcat.vitalsense.data.air;

public interface IAirQuality {
    boolean getActive();

    float getQuality();

    float getMaxQuality();

    void setActive(boolean active);

    void setQuality(float quality);

    /**
     * @return return 0 if the quality cannot be increased, otherwise return remainder of increase
     */
    default float tryIncreaseQuality(float quality) {
        // Can just add the quality
        if (getQuality() + quality <= getMaxQuality()) {
            setQuality(getQuality() + quality);
            return 0f;
        // Maximum has already been reached
        } else if (getQuality() == getMaxQuality()) {
            return 0f;
        // Increase as much as possible and return remainder
        } else {
            float newQuality = getMaxQuality() - getQuality();
            float remainder = quality - newQuality;
            setQuality(getQuality()+newQuality);
            return remainder;
        }
    }

    /**
     * @return return 0 if the quality cannot be increased, otherwise return remainder of increase
     */
    default float tryDecreaseQuality(float quality) {
        // Can just decrease the quality
        if (getQuality() - quality <= 0) {
            setQuality(getQuality() - quality);
            return 0f;
            // Maximum has already been reached
        } else if (getQuality() == 0) {
            return 0f;
            // Decrease as much as possible and return remainder
        } else {
            float remainder = quality - getQuality();
            setQuality(0);
            return remainder;
        }
    }
}
