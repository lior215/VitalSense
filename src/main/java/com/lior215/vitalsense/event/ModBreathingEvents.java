package com.lior215.vitalsense.event;

import api.AirQuality;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lior215.vitalsense.client.AirQualityOverlay.indicatorX;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public class ModBreathingEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (player != null) {
                if (player.getY() < 50 && !player.level.canSeeSky(player.getOnPos().offset(0,1,0))) {
                    if (AirQuality.getAirQuality() < 180) {
                        AirQuality.setAirQuality(AirQuality.getAirQuality() + 1);
                    }
                } else if (player.isUnderWater()) {
                    if (AirQuality.getAirQuality() < 180) {
                        AirQuality.setAirQuality(AirQuality.getAirQuality() + 2);
                    }
                } else {
                    if (AirQuality.getAirQuality() > 0) {
                        AirQuality.setAirQuality(AirQuality.getAirQuality() - 1);
                    }
                }
            }
        }
    }
}