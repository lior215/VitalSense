package com.lior215.vitalsense.event;

import com.lior215.vitalsense.capabilities.AirQuality;
import com.lior215.vitalsense.capabilities.AirQualityProvider;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lior215.vitalsense.vitalsense.LOGGER;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public class ModBreathingEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            event.player.getCapability(AirQualityProvider.airQualityCapability).ifPresent(airQuality -> {
                if (player.getY() < 50 && !player.level.canSeeSky(player.getOnPos().offset(0, 1, 0))) {
                    if (airQuality.getAirQuality() < 180) {
                        airQuality.setAirQuality(airQuality.getAirQuality() + 1);
                    }
                } else if (player.isUnderWater()) {
                    if (airQuality.getAirQuality() < airQuality.getMaxAirQuality()) {
                        airQuality.setAirQuality(airQuality.getAirQuality() + 2);
                    }
                } else if (player.isInLava()) {
                    if (airQuality.getAirQuality() < airQuality.getMaxAirQuality()) {
                        airQuality.setAirQuality(airQuality.getAirQuality() + 10);
                    }
                } else if (player.getY() > 150) {
                    if (airQuality.getAirQuality() < airQuality.getMaxAirQuality()) {
                        airQuality.setAirQuality(airQuality.getAirQuality() + 1);
                    }
                } else {
                    if (airQuality.getAirQuality() > 0) {
                        airQuality.setAirQuality(airQuality.getAirQuality() - 1);
                    }
                }
                if (airQuality.getAirQuality() <= 0) {
                    if (player.getAirSupply() > 50) {
                        player.setAirSupply(Math.min(player.getMaxAirSupply(), player.getAirSupply() - 4));
                        LOGGER.info("Air quality: " + airQuality.getAirQuality());
                        LOGGER.info("Air supply: " + player.getAirSupply() + ", max air supply: " + player.getMaxAirSupply());
                    }
                }
            });
        }
    }
}