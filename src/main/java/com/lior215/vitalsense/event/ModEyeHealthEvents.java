package com.lior215.vitalsense.event;

import com.lior215.vitalsense.effects.RedEyesEffect;
import com.lior215.vitalsense.mobeffects.ModMobEffects;
import com.lior215.vitalsense.mobeffects.RedEyes;
import com.lior215.vitalsense.network.ModPackets;
import com.lior215.vitalsense.network.packets.S2CEyeHealth;
import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.vitalsense;
import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
            if (!event.getObject().getCapability(EyeHealthProvider.eHealth).isPresent()) {
                event.addCapability(new ResourceLocation(vitalsense.MOD_ID, "eyehealthproperties"), new EyeHealthProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClonedEyeHealth(PlayerEvent.Clone event) { //when player dies reapply capability
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(EyeHealthProvider.eHealth).ifPresent(oldStore -> {
                event.getEntity().getCapability(EyeHealthProvider.eHealth).ifPresent(newStore -> {
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
                    event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
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

    @SubscribeEvent
    public static void onPlayerEyeHealthValues(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            if (timer.getTimer() <= 0) {
                timer.setTimerToStartValue();
            } else if (timer.getTimer() == 1) {
                event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                    if(eyeHealth.getHealthValue() <= 25) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(15);
                    } else if (eyeHealth.getHealthValue() > 25 && eyeHealth.getHealthValue() <= 50) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(37);
                    } else if (eyeHealth.getHealthValue() > 50) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(75);
                    }

                });
            }
        }
    }


    @SubscribeEvent
    public static void hasDisease(TickEvent.PlayerTickEvent event) {
        if(event.player.hasEffect(ModMobEffects.redeyes.get())) {
            RedEyesEffect.setRenderEyeDisease(true);
        } else {
            RedEyesEffect.setRenderEyeDisease(false);
            RedEyesEffect.setTransparency(0f);
            RedEyes.modEffectRedEyesReset();
        }
    }


}
