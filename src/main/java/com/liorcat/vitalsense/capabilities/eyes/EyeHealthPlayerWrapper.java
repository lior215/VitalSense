package com.liorcat.vitalsense.capabilities.eyes;

import com.liorcat.vitalsense.data.VSAttachmentTypes;
import com.liorcat.vitalsense.data.eyes.EyeHealth;
import com.liorcat.vitalsense.data.eyes.IEyeHealth;
import net.minecraft.world.entity.player.Player;

public class EyeHealthPlayerWrapper implements IEyeHealth {
    private final EyeHealth eyeHealth;

    public EyeHealthPlayerWrapper(Player player) {
        this.eyeHealth = player.getData(VSAttachmentTypes.EYE_HEALTH);
    }

    @Override
    public boolean getActive() {
        return eyeHealth.isActive();
    }

    @Override
    public float getHealth() {
        return eyeHealth.getHealth();
    }

    @Override
    public float getMinHealth() {
        return eyeHealth.getMinHealth();
    }

    @Override
    public float getMaxHealth() {
        return eyeHealth.getMaxHealth();
    }

    @Override
    public void setActive(boolean active) {
        eyeHealth.setActive(active);
    }

    @Override
    public void setHealth(float health) {
        eyeHealth.setHealth(health);
    }

    @Override
    public void setMinHealth(float minHealth) {
        eyeHealth.setMinHealth(minHealth);
    }

    @Override
    public void setMaxHealth(float maxHealth) {
        eyeHealth.setMaxHealth(maxHealth);
    }
}
