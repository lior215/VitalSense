package com.liorcat.vitalsense.registries.mobeffects;

import com.liorcat.vitalsense.registries.effects.PhotophobiaEffect;
import com.liorcat.vitalsense.utils.TimerProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

public class Photophobia extends MobEffect {

    static TimerProvider delayEffectTimer = new TimerProvider(21);

    private static float multiplier = 0f;
    private static int frame;


    public Photophobia(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public static void modEffectphotophobiaReset() {
        multiplier = 0;
        delayEffectTimer.setTimerToStartValue();
    }

    public static float getMultiplier() {
        return multiplier;
    }

    private void frameCycle(LivingEntity living) {
        if (frame < 4) {
            frame++;
        } else {
            frame = 1;
        }
        PhotophobiaEffect.setFrame(frame);
        living.sendSystemMessage(Component.literal(" "+frame));
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int LightLevelOnBlock = pLivingEntity.level().getBrightness(LightLayer.BLOCK, pLivingEntity.getOnPos().offset(0,+2,0));


        if(delayEffectTimer.getTimer() == 1) {
            if(LightLevelOnBlock >= 12) {
                frameCycle(pLivingEntity);


                if(multiplier < 1.0f) {
                    multiplier += 0.1;
                }

            } else if (LightLevelOnBlock < 12){
                if (multiplier > 0) {
                    multiplier -= 0.25f;
                    if (multiplier < 0) {
                        multiplier = 0;
                        frame = 0;
                    }
                }
            }
            PhotophobiaEffect.setTransparency(0.0f + multiplier);

            if(pLivingEntity.level().canSeeSky(pLivingEntity.getOnPos().offset(0,+2,0)) && pLivingEntity.level().isDay()) {
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));

            }
        } else if (delayEffectTimer.getTimer() == 0) {
            delayEffectTimer.setTimerToStartValue();
        }
        delayEffectTimer.decreaseTimer();

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

}
