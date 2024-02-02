package com.liorcat.vitalsense.event;

import com.liorcat.vitalsense.utils.TimerProvider;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.TickEvent;

public class EyeHealthEvents {
    private static final TimerProvider TIMER = new TimerProvider(50);

    public static void timerCountDown(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            TIMER.decreaseTimer();
        }
    }

    public static void damagePlayerUnderWater(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            if (TIMER.getTimer() <= 0) {
                TIMER.setTimerToStartValue();
            } else if (TIMER.getTimer() == 1) {
                    /*
                    event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                        if (event.player.isEyeInFluidType(Fluids.WATER.getFluidType())) {
                            eyeHealth.reduceHealthValue(2.5f);
                            event.player.sendSystemMessage(Component.literal("NOOO MY EYESS " + eyeHealth.getHealthValue()));
                            ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) event.player);
                        } else if (event.player.isEyeInFluidType(Fluids.LAVA.getFluidType())) {
                            eyeHealth.reduceHealthValue(25f);
                            event.player.sendSystemMessage(Component.literal("NOOO MY EYESS " + eyeHealth.getHealthValue()));
                            ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) event.player);
                        }
                    });
                     */
            }
        }
    }

    public static void tickPlayerEyeHealthValues(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            if (TIMER.getTimer() <= 0) {
                TIMER.setTimerToStartValue();
            } else if (TIMER.getTimer() == 1) {
                /*
                event.player.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
                    if(eyeHealth.getHealthValue() <= 25) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(15);
                    } else if (eyeHealth.getHealthValue() > 25 && eyeHealth.getHealthValue() <= 50) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(37);
                    } else if (eyeHealth.getHealthValue() > 50) {
                        ModBlinkingTimerEvents.setBlinkCountdownTimer(75);
                    }

                });
                 */
            }
        }
    }
}
