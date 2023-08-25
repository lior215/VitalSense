package com.lior215.vitalsense.mobeffects;

import com.lior215.vitalsense.effects.GlaucomaEffect;
import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.VitalSense;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public class Glaucoma extends MobEffect implements INBTSerializable<CompoundTag> {


    private static TimerProvider delayEffectTimer = new TimerProvider(11);
    private static float multiplier = 0f;

    public static void modEffectGlaucomaReset() {
        multiplier = 0;
        delayEffectTimer.setTimerToStartValue();
    }


    protected Glaucoma(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }



    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {

        if(delayEffectTimer.getTimer() == 1) {
            if(multiplier < 1f) {
                multiplier += 0.01f;
            }
            GlaucomaEffect.setTransparency(0.0f + multiplier);
        } else if (delayEffectTimer.getTimer() == 0) {
            delayEffectTimer.setTimerToStartValue();
        }
        delayEffectTimer.decreaseTimer();

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }



    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }





    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("glaucoma_multiplier", multiplier);
        nbt.putBoolean("has_glaucoma", GlaucomaEffect.getRenderDisease());
    }


    public void loadNBTData(CompoundTag nbt) {
        VitalSense.LOGGER.info("loaded nbt data glaucoma");
        multiplier = nbt.getFloat("glaucoma_multiplier");
        GlaucomaEffect.setRenderEyeDisease(nbt.getBoolean("has_glaucoma"));
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
