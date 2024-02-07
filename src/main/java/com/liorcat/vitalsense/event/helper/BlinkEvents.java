package com.liorcat.vitalsense.event.helper;

import com.liorcat.vitalsense.utils.TimerProvider;
import net.neoforged.neoforge.event.TickEvent;

public class BlinkEvents {
    public static TimerProvider blinkTimerToBlink = new TimerProvider(75);
    private static boolean playerBlinking = true;
    private static boolean canStartBlinkingTimer = false;
    private static int blinkCountdownTimer = blinkTimerToBlink.getStartTimerValue();


    public static int getBlinkCountdownTimer() {
        return blinkCountdownTimer;
    }

    public static void setCanStartBlinkingTimer(boolean value) {
        canStartBlinkingTimer = value;
    }

    public static boolean getPlayerBlinking() {
        return playerBlinking;
    }

    public static void setPlayerBlinking(boolean value) {
        playerBlinking = value;
    }

    public static void setBlinkCountdownTimer(int value) {
        blinkCountdownTimer = value;
    }

    public static void playerBlinkTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase == TickEvent.Phase.START && canStartBlinkingTimer) {
            if (blinkTimerToBlink.getTimer() <= 0) {
                blinkTimerToBlink.setTimer(blinkCountdownTimer);
            } else if (blinkTimerToBlink.getTimer() == 1) {
                //Debug: event.player.sendSystemMessage(Component.literal("Start Blinked!"));
                playerBlinking = true;
                canStartBlinkingTimer = false;
            }
            blinkTimerToBlink.decreaseTimer();
            //Debug: blinkTimerToBlink.printTimerInChat("BlinkEvent", event.player);
        }

    }
}
