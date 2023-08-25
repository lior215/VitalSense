package com.lior215.vitalsense.event;

import com.lior215.vitalsense.capabilities.AirQualityProvider;
import com.lior215.vitalsense.VitalSense;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, value = Dist.CLIENT)
public class ModBreathingEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        event.player.getCapability(AirQualityProvider.airQualityCapability).ifPresent(airQuality -> {
            VitalSense.LOGGER.info("Registered: " + airQuality);
        });
    }
}