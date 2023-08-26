package com.lior215.vitalsense.event;

import com.lior215.vitalsense.VitalSense;
import com.lior215.vitalsense.capabilities.AirQualityProvider;
import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, value = Dist.CLIENT)
public class ModBreathingEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayerEyeHealth(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(AirQualityProvider.airQual).isPresent()) {
                event.addCapability(new ResourceLocation(VitalSense.MOD_ID, "airqualityproperties"), new AirQualityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClonedEyeHealth(PlayerEvent.Clone event) { //when player dies reapply capability
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(AirQualityProvider.airQual).ifPresent(oldStore -> {
                event.getEntity().getCapability(AirQualityProvider.airQual).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
}