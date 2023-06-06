package it.lior215.vitalsense.Event;

import it.lior215.vitalsense.Capabilities.TimerProvider;
import it.lior215.vitalsense.vitalsense;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID)
public class ModBlinkingEvents {
    //BLINKING
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayerBlink(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(TimerProvider.Timer).isPresent()) {
                event.addCapability(new ResourceLocation(vitalsense.MOD_ID, "blinkproperties"), new TimerProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClonedBlink(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(TimerProvider.Timer).ifPresent(oldStore -> {
                event.getEntity().getCapability(TimerProvider.Timer).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

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



        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START && canStartBlinkingTimer) {
            event.player.getCapability(TimerProvider.Timer).ifPresent(timer -> {
                if (timer.getTimer() <= 0) {
                    timer.setTimer(150);
                } else if (timer.getTimer() == 11) {
                    event.player.sendSystemMessage(Component.literal("Start Blinked!"));
                    timer.decreaseTimer();
                    playerBlinking = true;
                    canStartBlinkingTimer = false;
                } else if (timer.getTimer() == 1) {
                    timer.decreaseTimer();
                    //playerBlinking = false;
                    //Debug: event.player.sendSystemMessage(Component.literal("Timer: "+timer.getTimer()));
                } else {
                    timer.decreaseTimer();
                }
            });
        }

    }


    @Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public class BlinkOverlay {
       /* @SubscribeEvent
        public static void playerBlink(RenderGuiOverlayEvent.Post event) {
                BlinkEffectScreen.Render(screenDivider);
        }*/
    }
}

