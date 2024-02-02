package com.liorcat.vitalsense.event;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.capabilities.eyes.EyeHealthPlayerWrapper;
import com.liorcat.vitalsense.client.AirQualityOverlay;
import com.liorcat.vitalsense.client.BlinkHud;
import com.liorcat.vitalsense.registries.VSKeyBindings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class VSEvents {
    @Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(VSKeyBindings.OPEN_EYE_GUI);
        }

        @SubscribeEvent
        public static void registerGuiOverlay(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll(new ResourceLocation(VitalSense.MOD_ID, "air_quality"), AirQualityOverlay.HUD_AIR);
            event.registerAboveAll(new ResourceLocation(VitalSense.MOD_ID, "quality_indicator"), AirQualityOverlay.HUD_QUALITY_INDICATOR);
            event.registerAboveAll(new ResourceLocation(VitalSense.MOD_ID, "blink_hud"), BlinkHud.BLINK_HUD);
            //event.registerBelow(VanillaGuiOverlay.VIGNETTE.id(),"red_eyes_disease", RedEyesEffect.RED_EYES_DISEASE);
        }
    }

    @Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Server {
        @SubscribeEvent
        public static void onAttachCapabilitiesPlayerEyeHealth(RegisterCapabilitiesEvent event) {
            event.registerEntity(VSCapabilities.EyeHealth.ENTITY, EntityType.PLAYER, (player, ctx) -> new EyeHealthPlayerWrapper(player));
        }
    }
}
