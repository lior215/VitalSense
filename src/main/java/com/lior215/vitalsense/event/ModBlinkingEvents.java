package com.lior215.vitalsense.event;

import com.lior215.vitalsense.utils.TimerProvider;
import com.lior215.vitalsense.vitalsense;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public class ModBlinkingEvents {

    private static TimerProvider timer = new TimerProvider(75);

    private static boolean playerBlinking = true;
    private static boolean canStartBlinkingTimer = false;

    public static void setCanStartBlinkingTimer(boolean value) {
        canStartBlinkingTimer = value;
    }
    
    public static boolean getPlayerBlinking() {
        return playerBlinking;
    }

    public static void setPlayerBlinking(boolean value) {
        playerBlinking = value;
    }
    @SubscribeEvent
    public static void onPlayerTickBlink(TickEvent.PlayerTickEvent event) {

        if (event.side.isClient() && event.phase == TickEvent.Phase.START && canStartBlinkingTimer) {
                if (timer.getTimer() <= 0) {
                    timer.setTimerToStartValue();
                } else if (timer.getTimer() == 1) {
                    event.player.sendSystemMessage(Component.literal("Start Blinked!"));
                    playerBlinking = true;
                    canStartBlinkingTimer = false;
                }
                timer.decreaseTimer();
                //Debug:timer.printTimerInChat("BlinkEvent", event.player);
        }

    }
}


