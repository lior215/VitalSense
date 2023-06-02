package it.lior215.vitalsense.Event;

import com.mojang.blaze3d.vertex.PoseStack;
import it.lior215.vitalsense.Capabilities.EyeHealthProvider;
import it.lior215.vitalsense.Capabilities.TimerProvider;
import it.lior215.vitalsense.Screen.BlinkEffectScreen;
import it.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
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

    @SubscribeEvent
    public static void onPlayerTickBlink(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            event.player.getCapability(TimerProvider.Timer).ifPresent(timer -> {

                if(timer.getTimer() == 0) {
                    timer.setTimer(140);

                } else if (timer.getTimer() == 1) {
                    event.player.sendSystemMessage(Component.literal("Blinked!"));
                    timer.decreaseTimer();
                } else {
                    timer.decreaseTimer();
                     //Debug: event.player.sendSystemMessage(Component.literal("Timer: "+timer.getTimer()));

                }

            });
        }
    }
}
