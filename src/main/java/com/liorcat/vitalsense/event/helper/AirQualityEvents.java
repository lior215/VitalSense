package com.liorcat.vitalsense.event.helper;

import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.data.air.IAirQuality;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.TickEvent;

public class AirQualityEvents {
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT) {
            Player player = event.player;
            IAirQuality airQuality = player.getCapability(VSCapabilities.AirQuality.ENTITY);
            if (player.isUnderWater()) {
                if (event.player.level().getGameTime() % 20 == 0) {
                    airQuality.tryDecreaseQuality(1);
                }
            }
        }
    }
}
