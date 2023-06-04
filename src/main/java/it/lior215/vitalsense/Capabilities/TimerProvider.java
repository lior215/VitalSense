package it.lior215.vitalsense.Capabilities;

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

public class TimerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<Timer> Timer = CapabilityManager.get(new CapabilityToken<Timer>() {});

    public Timer timer = null;


    public final LazyOptional<Timer> optional = LazyOptional.of(this::initializeTimer);

    private int timerStartValue;


    public TimerProvider() {
        this.timerStartValue = 0;
    }

    public TimerProvider(int timerStartValue) {
        this.timerStartValue = timerStartValue;
    }


    private Timer initializeTimer() {
        if(this.timer == null) {
            this.timer = new Timer(timerStartValue);
        }

        return this.timer;
    }





    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == Timer) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        initializeTimer().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        initializeTimer().loadNBTData(nbt);
    }
}
