package com.liorcat.vitalsense.event;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.event.helper.AirQualityEvents;
import com.liorcat.vitalsense.event.helper.BlinkEvents;
import com.liorcat.vitalsense.event.helper.EyeEffectEvent;
import com.liorcat.vitalsense.event.helper.EyeHealthEvents;
import com.liorcat.vitalsense.registries.VSKeyBindings;
import com.liorcat.vitalsense.registries.commands.EyeHealthInfo;
import com.liorcat.vitalsense.registries.commands.EyeHealthSetCommand;
import com.liorcat.vitalsense.registries.screen.EyeGui;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

public class NFEvents {
    @Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (VSKeyBindings.OPEN_EYE_GUI.consumeClick()) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.setScreen(new EyeGui());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = VitalSense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Common {
        @SubscribeEvent
        public static void onCommandRegister(RegisterCommandsEvent event) {
            new EyeHealthSetCommand(event.getDispatcher());
            new EyeHealthInfo(event.getDispatcher());

            ConfigCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event) {
            AirQualityEvents.playerTick(event);
            BlinkEvents.playerBlinkTick(event);
            EyeHealthEvents.tickPlayerEyeHealthValues(event);
            EyeHealthEvents.damagePlayerUnderWater(event);
            EyeHealthEvents.tickPlayerEyeHealthValues(event);
            EyeHealthEvents.timerCountDown(event);
            EyeEffectEvent.redEyeEffectTick(event);
            EyeEffectEvent.damageRedEyes(event);
            EyeEffectEvent.tunnelVisionEffectTick(event);
            EyeEffectEvent.damagePhotophobiaEyes(event);
        }
    }
}
