package com.liorcat.vitalsense.event;

import com.liorcat.vitalsense.registries.VSMobEffects;
import com.liorcat.vitalsense.registries.effects.RedEyesEffect;
import com.liorcat.vitalsense.registries.effects.TunnelVisionEffect;
import com.liorcat.vitalsense.registries.mobeffects.RedEyes;
import com.liorcat.vitalsense.registries.mobeffects.TunnelVision;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.TickEvent;

import java.util.Random;

public class EyeEffectEvent {

    public static void redEyeEffectTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.hasEffect(VSMobEffects.REDEYES.get())) {
            RedEyesEffect.setRenderEyeDisease(false);
            RedEyesEffect.setTransparency(0f);
            RedEyes.modEffectRedEyesReset();
            if (event.player.level().getDayTime() % 24000 == 18000 && event.phase == TickEvent.Phase.END && event.side.isServer()) {
                // TODO: use the level's randomsource for this?
                float checkIfInfected = new Random().nextFloat(10.0f);
                event.player.sendSystemMessage(Component.literal(" " + checkIfInfected));
                if (checkIfInfected >= 7.5f) {
                    RedEyesEffect.setRenderEyeDisease(true);
                    event.player.addEffect(new MobEffectInstance(VSMobEffects.REDEYES.get(), 1000000000));
                }
                checkIfInfected = 0;
            }
        } else if (event.player.hasEffect(VSMobEffects.REDEYES.get())) {
            RedEyesEffect.setRenderEyeDisease(true);
        }
    }

    public static void damageRedEyes(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(VSMobEffects.REDEYES.get()) && event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            /*
            event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                if (RedEyes.getMultiplier() >= 1.0f) {
                    if (damageRedEyeTimer.getTimer() == 1) {
                        eyeHealth.reduceHealthValue(0.25f);
                        ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) event.player);
                    } else if (damageRedEyeTimer.getTimer() <= 0) {
                        damageRedEyeTimer.setTimerToStartValue();
                    }
                    damageRedEyeTimer.decreaseTimer();
                } else {
                    damageRedEyeTimer.setTimerToStartValue();
                }
            });
             */
        }
    }

    public static void tunnelVisionEffectTick(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(VSMobEffects.TUNNEL_VISION.get())) {
            TunnelVisionEffect.setRenderEyeDisease(true);
        } else {
            TunnelVisionEffect.setRenderEyeDisease(false);
            TunnelVisionEffect.setTransparency(0f);
            TunnelVision.modEffectGlaucomaReset();
        }
    }

    public static void damagePhotophobiaEyes(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(VSMobEffects.PHOTOPHOBIA.get()) && event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            /*
            event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                if (Photophobia.getMultiplier() >= 1.0f) {
                    if (damagePhotophobiaTimer.getTimer() == 1) {
                        eyeHealth.reduceHealthValue(0.25f);
                        event.player.hurt(DamageSource.GENERIC, 3f);

                        ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) event.player);
                    } else if (damagePhotophobiaTimer.getTimer() <= 0) {
                        damagePhotophobiaTimer.setTimerToStartValue();
                    }
                    damagePhotophobiaTimer.decreaseTimer();
                } else {
                    damagePhotophobiaTimer.setTimerToStartValue();
                }
            });
             */
        }
    }
}
