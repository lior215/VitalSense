package com.lior215.vitalsense.event;

import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import com.lior215.vitalsense.network.ModPackets;
import com.lior215.vitalsense.network.S2CEyeHealth;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public class ModBreathingEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                if (event.player.isEyeInFluidType(Fluids.WATER.getFluidType()) == true) {
                    eyeHealth.reduceHealthValue(2.5f);
                    event.player.sendSystemMessage(Component.literal("NOOO MY EYESS " + eyeHealth.getHealthValue()));
                } else if (event.player.isEyeInFluidType(Fluids.LAVA.getFluidType())) {
                    eyeHealth.reduceHealthValue(25f);
                    event.player.sendSystemMessage(Component.literal("NOOO MY EYESS " + eyeHealth.getHealthValue()));
                }
                ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) event.player);
            });
        }
    }
}