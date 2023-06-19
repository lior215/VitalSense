package com.lior215.vitalsense.event;

import com.lior215.vitalsense.client.AirQualityOverlay;
import com.lior215.vitalsense.client.BlinkHud;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModbusEvents {
        @SubscribeEvent
        public static void registerGuiOverlay(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("air_quality", AirQualityOverlay.HUD_AIR);
            event.registerAboveAll("quality_indicator", AirQualityOverlay.HUD_QUALITY_INDICATOR);
            event.registerAboveAll("blink_hud", BlinkHud.BLINK_HUD);
        }

    }


}