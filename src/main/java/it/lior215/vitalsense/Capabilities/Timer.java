package it.lior215.vitalsense.Capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Timer {

    int timer = 0;
    boolean autoblink = true;


    public Timer(int startValue) {
        this.timer = startValue;
    }


    public int getTimer() {
        return this.timer;
    }
    public void setTimer(int timer) {
        this.timer = timer;
    }
    public void decreaseTimer() {
        this.timer = this.timer -= 1;
    }








    public void copyFrom(Timer source) {
        this.timer = source.timer;
    }


    public void saveNBTData(CompoundTag nbt) { //crea il data NBT mettendo l'int all'interno
        nbt.putInt("timer", timer);
    }


    public void loadNBTData(CompoundTag nbt) { //carica il data NBT prendendo l'int all'interno
        nbt.getInt("timer");
    }



}
