package com.liorcat.vitalsense.event;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.registries.VSKeyBindings;
import com.liorcat.vitalsense.registries.VSMobEffects;
import com.liorcat.vitalsense.registries.commands.EyeHealthInfo;
import com.liorcat.vitalsense.registries.commands.EyeHealthSetCommand;
import com.liorcat.vitalsense.registries.effects.RedEyesEffect;
import com.liorcat.vitalsense.registries.effects.TunnelVisionEffect;
import com.liorcat.vitalsense.registries.mobeffects.RedEyes;
import com.liorcat.vitalsense.registries.mobeffects.TunnelVision;
import com.liorcat.vitalsense.registries.screen.EyeGui;
import com.liorcat.vitalsense.utils.TimerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.util.Random;

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
