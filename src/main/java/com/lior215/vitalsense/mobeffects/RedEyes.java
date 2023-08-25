package com.lior215.vitalsense.mobeffects;

import com.lior215.vitalsense.effects.RedEyesEffect;
import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.VitalSense;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public class RedEyes extends MobEffect implements INBTSerializable<CompoundTag> {



    private static TimerProvider delayEffectTimer = new TimerProvider(11);
    private static float multiplier = 0f;

    protected RedEyes(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public static void modEffectRedEyesReset() {
        multiplier = 0;
        delayEffectTimer.setTimerToStartValue();
    }

    public static float getMultiplier() {
        return multiplier;
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {

        if(delayEffectTimer.getTimer() == 1) {
            if(multiplier < 1.0f) {
                multiplier += 0.015;
            }
            RedEyesEffect.setTransparency(0.0f + multiplier);
        } else if (delayEffectTimer.getTimer() == 0) {
            delayEffectTimer.setTimerToStartValue();
        }
        delayEffectTimer.decreaseTimer();
        //Eye damager in ServerDiseaseManagerEvents.java
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }









    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }



    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("redeyes_multiplier", multiplier);
        nbt.putBoolean("has_redeyes", RedEyesEffect.getRenderDisease());
    }


    public void loadNBTData(CompoundTag nbt) {
        VitalSense.LOGGER.info("loaded nbt data glaucoma");
        multiplier = nbt.getFloat("redeyes_multiplier");
        RedEyesEffect.setRenderEyeDisease(nbt.getBoolean("has_redeyes"));
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        loadNBTData(nbt);
    }


}
