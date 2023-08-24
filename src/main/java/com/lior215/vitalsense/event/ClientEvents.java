package com.lior215.vitalsense.event;

import com.lior215.vitalsense.client.AirQualityOverlay;
import com.lior215.vitalsense.client.BlinkHud;
import com.lior215.vitalsense.effects.RedEyesEffect;
import com.lior215.vitalsense.keybindings.ModKeyBindings;
import com.lior215.vitalsense.screen.EyeGui;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
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
            //event.registerBelow(VanillaGuiOverlay.VIGNETTE.id(),"red_eyes_disease", RedEyesEffect.RED_EYES_DISEASE);
        }

    }
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        vitalsense.LOGGER.info("key registrata");
        event.register(ModKeyBindings.OPEN_EYE_GUI);
    }




@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public static class ClientOnlyEvents {
    static Player player = Minecraft.getInstance().player;
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeyBindings.OPEN_EYE_GUI.consumeClick()) {
            Minecraft.getInstance().setScreen(new EyeGui());
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("hah"));
        }
    }



}
}
