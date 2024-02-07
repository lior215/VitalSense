package com.liorcat.vitalsense.networking;

import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.data.eyes.IEyeHealth;
import com.liorcat.vitalsense.networking.data.EyeHealthData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class EyeHealthPayload {
    private static final EyeHealthPayload INSTANCE = new EyeHealthPayload();

    public static EyeHealthPayload getInstance() {
        return INSTANCE;
    }

    public void handleData(final EyeHealthData data, final PlayPayloadContext ignored) {
        Player player = Minecraft.getInstance().player;
        IEyeHealth eyeHealth = player.getCapability(VSCapabilities.EyeHealth.ENTITY);
        eyeHealth.setActive(data.active());
        eyeHealth.setHealth(data.health());
        eyeHealth.setMinHealth(data.minHealth());
        eyeHealth.setMaxHealth(data.maxHealth());
    }
}
