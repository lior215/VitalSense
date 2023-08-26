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

public class AirQualityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AirQuality> airQual = CapabilityManager.get(new CapabilityToken<AirQuality>() {
    });

    private AirQuality airQuality = null;

    public final LazyOptional<AirQuality> optional = LazyOptional.of(this::initializeAirQuality);

    private AirQuality initializeAirQuality() {
        if (this.airQuality == null) {
            this.airQuality = new AirQuality();
        }
        return this.airQuality;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == airQual) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initializeAirQuality().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initializeAirQuality().loadNBTData(nbt);
    }
}
