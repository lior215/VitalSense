package com.lior215.vitalsense.event;

import com.lior215.vitalsense.client.BlinkHud;
import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.vitalsense;
import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID)
public class ModEyeHealthEvents {

    private static TimerProvider timer = new TimerProvider(50);


    //EYE HEALTH

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayerEyeHealth(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(EyeHealthProvider.EyeHealth).isPresent()) {
                event.addCapability(new ResourceLocation(vitalsense.MOD_ID, "eyehealthproperties"), new EyeHealthProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClonedEyeHealth(PlayerEvent.Clone event) { //when player dies reapply capability
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(EyeHealthProvider.EyeHealth).ifPresent(oldStore -> {
                event.getEntity().getCapability(EyeHealthProvider.EyeHealth).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
    @SubscribeEvent
    public static void timerCountdown(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            timer.decreaseTimer();
        }
    }


    @SubscribeEvent
    public static void onPlayerUnderWaterEyeDamage(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
                if (timer.getTimer() <= 0) {
                    timer.setTimerToStartValue();
                } else if (timer.getTimer() == 1) {
                    event.player.getCapability(EyeHealthProvider.EyeHealth).ifPresent(eyeHealth -> {
                        if (event.player.isEyeInFluidType(Fluids.WATER.getFluidType()) == true) {
                            eyeHealth.reduceHealthValue(2.5f);
                            event.player.sendSystemMessage(Component.literal("NOOO MY EYESS " + eyeHealth.getHealthValue()));
                        } else if (event.player.isEyeInFluidType(Fluids.LAVA.getFluidType())) {
                            eyeHealth.reduceHealthValue(25f);
                            event.player.sendSystemMessage(Component.literal("NOOO MY EYESS " + eyeHealth.getHealthValue()));
                        }
                    });
                }
        }
    }

    @SubscribeEvent
    public static void onPlayerEyeHealthValues(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            if (timer.getTimer() <= 0) {
                timer.setTimerToStartValue();
            } else if (timer.getTimer() == 1) {
                event.player.getCapability(EyeHealthProvider.EyeHealth).ifPresent(eyeHealth -> {
                    if(eyeHealth.getHealthValue() <= 50) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(37);
                        BlinkHud.setShouldRenderFaster(true);
                    } else if (eyeHealth.getHealthValue() > 50) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(75);
                        BlinkHud.setShouldRenderFaster(false);
                    }

                });
            }
        }
    }
}
