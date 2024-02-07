package com.liorcat.vitalsense.capabilities.air;

import com.liorcat.vitalsense.data.VSAttachmentTypes;
import com.liorcat.vitalsense.data.air.AirQuality;
import com.liorcat.vitalsense.data.air.IAirQuality;
import net.minecraft.world.entity.player.Player;

public class AirQualityPlayerWrapper implements IAirQuality {
    private final AirQuality airQuality;

    public AirQualityPlayerWrapper(Player player) {
        this.airQuality = player.getData(VSAttachmentTypes.AIR_QUALITY);
    }

    @Override
    public boolean getActive() {
        return airQuality.isActive();
    }

    @Override
    public float getQuality() {
        return airQuality.getQuality();
    }

    @Override
    public float getMaxQuality() {
        return 150;
    }

    @Override
    public void setActive(boolean active) {
        airQuality.setActive(active);
    }

    @Override
    public void setQuality(float health) {
        airQuality.setQuality(health);
    }
}
