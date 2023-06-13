package com.lior215.vitalsense.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EyeHealthProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<EyeHealth> eHealth = CapabilityManager.get(new CapabilityToken<EyeHealth>() { });

    private EyeHealth eyeHealth = null;


    public final LazyOptional<EyeHealth> optional = LazyOptional.of(this::initializeEyeHealth);

    private EyeHealth initializeEyeHealth() {
        if(this.eyeHealth == null) {
            this.eyeHealth = new EyeHealth();
        }

        return this.eyeHealth;
    }





    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == eHealth) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initializeEyeHealth().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initializeEyeHealth().loadNBTData(nbt);
    }



}
