package com.lior215.vitalsense.event;

import com.lior215.vitalsense.capabilities.EyeHealthProvider;
import com.lior215.vitalsense.effects.photophobiaEffect;
import com.lior215.vitalsense.effects.GlaucomaEffect;
import com.lior215.vitalsense.effects.RedEyesEffect;
import com.lior215.vitalsense.mobeffects.photophobia;
import com.lior215.vitalsense.mobeffects.Glaucoma;
import com.lior215.vitalsense.mobeffects.ModMobEffects;
import com.lior215.vitalsense.mobeffects.RedEyes;
import com.lior215.vitalsense.network.ModPackets;
import com.lior215.vitalsense.network.packets.S2CEyeHealth;
import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;


@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID)
public class ServerDiseaseManagerEvents {


    //RED EYES
    private static boolean playerHasRedEyes = false;

    @SubscribeEvent
    public static void hasRedEyesDisease(TickEvent.PlayerTickEvent event) {
        if (!event.player.hasEffect(ModMobEffects.redeyes.get())) {
            RedEyesEffect.setRenderEyeDisease(false);
            RedEyesEffect.setTransparency(0f);
            RedEyes.modEffectRedEyesReset();
            if (event.player.level.getDayTime() % 24000 == 18000 && event.phase == TickEvent.Phase.END && event.side.isServer()) {
                float checkIfInfected = new Random().nextFloat(10.0f);
                event.player.sendSystemMessage(Component.literal(" " + checkIfInfected));
                if (checkIfInfected >= 7.5f) {
                    RedEyesEffect.setRenderEyeDisease(true);
                    event.player.addEffect(new MobEffectInstance(ModMobEffects.redeyes.get(), 1000000000));
                }
                checkIfInfected = 0;
            }
        } else if (event.player.hasEffect(ModMobEffects.redeyes.get())) {
            RedEyesEffect.setRenderEyeDisease(true);
        }
    }

    private static TimerProvider damageRedEyeTimer = new TimerProvider(21);

    @SubscribeEvent
    public static void RedEyesDamageEye(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(ModMobEffects.redeyes.get()) && event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
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
        }
    }


    //GLAUCOMA

    @SubscribeEvent
    public static void hasGlaucomaDisease(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(ModMobEffects.glaucoma.get())) {
            GlaucomaEffect.setRenderEyeDisease(true);
        } else {
            GlaucomaEffect.setRenderEyeDisease(false);
            GlaucomaEffect.setTransparency(0f);
            Glaucoma.modEffectGlaucomaReset();
        }
    }



    //FOTOFOBIA



    @SubscribeEvent
    public static void hasphotophobiaDisease(TickEvent.PlayerTickEvent event) {
        if (!event.player.hasEffect(ModMobEffects.photophobia.get())) {
            photophobiaEffect.setRenderEyeDisease(false);
            photophobiaEffect.setTransparency(0f);
            photophobia.modEffectphotophobiaReset();
            if (event.player.level.getDayTime() % 24000 == 18000 && event.phase == TickEvent.Phase.END && event.side.isServer()) {
                float checkIfInfected = new Random().nextFloat(10.0f);
                event.player.sendSystemMessage(Component.literal(" " + checkIfInfected));
                if (checkIfInfected >= 7.5f) {
                    photophobiaEffect.setRenderEyeDisease(true);
                    event.player.addEffect(new MobEffectInstance(ModMobEffects.photophobia.get(), 1000000000));
                }
                checkIfInfected = 0;
            }
        } else if (event.player.hasEffect(ModMobEffects.photophobia.get())) {
            photophobiaEffect.setRenderEyeDisease(true);
        }
    }

    private static TimerProvider damageFotoFobiaTimer = new TimerProvider(21);
    @SubscribeEvent
    public static void photophobiaDamageEye(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(ModMobEffects.photophobia.get()) && event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                if (photophobia.getMultiplier() >= 1.0f) {
                    if (damageFotoFobiaTimer.getTimer() == 1) {
                        eyeHealth.reduceHealthValue(0.25f);
                        event.player.hurt(DamageSource.GENERIC, 3f);

                        ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) event.player);
                    } else if (damageFotoFobiaTimer.getTimer() <= 0) {
                        damageFotoFobiaTimer.setTimerToStartValue();
                    }
                    damageFotoFobiaTimer.decreaseTimer();
                } else {
                    damageFotoFobiaTimer.setTimerToStartValue();
                }
            });
        }
    }


    //Light sensibility

}
