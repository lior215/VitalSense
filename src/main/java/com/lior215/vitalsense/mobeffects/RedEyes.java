package com.lior215.vitalsense.mobeffects;

import com.lior215.vitalsense.effects.RedEyesEffect;
import com.lior215.vitalsense.utils.TimerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class RedEyes extends MobEffect {

    Minecraft mc = Minecraft.getInstance();
    static RedEyesEffect effectOnScreen = null;
    static TimerProvider timer = new TimerProvider(10);
    private static float multiplier = 0f;

    public static RedEyesEffect getEffectOnScreen() {
        return effectOnScreen;
    }
    public static void setEffectOnScreen(RedEyesEffect redEyesEffect) {
        effectOnScreen = redEyesEffect;
    }

    protected RedEyes(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public static void modEffectRedEyesReset() {
        multiplier = 0;
        timer.setTimerToStartValue();
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) { //TODO: risolvere il ALREADY BUILDING EXCEPTION, viene chiamato troppo velocemente il builder, forse un timer risolve
        if(timer.getTimer() == 1) {
            RedEyesEffect.setTransparency(0.0f + multiplier);
            pLivingEntity.sendSystemMessage(Component.literal("effetto"));
        } else if (timer.getTimer() == 0) {
            timer.setTimerToStartValue();
        }
        timer.decreaseTimer();



        if(multiplier < 1.0f) {
            multiplier += 0.005;
        }


    super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
