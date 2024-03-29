package com.liorcat.vitalsense.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class TimerProvider {
    private int timer = 0;

    private int startTimerValue = 0;


    public TimerProvider(int startTimerValue) {
        setTimer(startTimerValue);
        this.startTimerValue = startTimerValue;
    }


    public int getTimer() {
        return timer;
    }
    public int getStartTimerValue() { return startTimerValue; }
    
    public void setTimer(int value) {
        timer = value;
    }
    
    public void decreaseTimer() {
        timer -= 1;
    }

    public void decreaseTimerWithValue(int value) {
        timer -= value;
    }

    public void setTimerToStartValue() {
        timer = startTimerValue;
    }

    public void printTimerInChat(@Nullable String prefix, Player player) {
        if (prefix == null) {
            prefix = "TimerProvider";
        }
        player.sendSystemMessage(Component.literal("["+prefix+ "]-"+"Timer: "+getTimer()));
    }

    public void printTimerInConsole(@Nullable String prefix) {
        if (prefix == null) {
            prefix = "TimerProvider";
        }
        System.out.println("[" + prefix + "]-" + "Timer: " + getTimer());
    }

}
