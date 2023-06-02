package it.lior215.vitalsense.Event;

import it.lior215.vitalsense.Capabilities.EyeHealthProvider;
import it.lior215.vitalsense.Capabilities.TimerProvider;
import it.lior215.vitalsense.vitalsense;
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
public class ModEyeHealthEvents {



    //EYE HEALTH

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayerEyeHealth(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(EyeHealthProvider.EyeHealth).isPresent()) {
                event.addCapability(new ResourceLocation(vitalsense.MOD_ID, "eyehealthproperties"), new EyeHealthProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClonedEyeHealth(PlayerEvent.Clone event) { //when player dies reapply capability
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(EyeHealthProvider.EyeHealth).ifPresent(oldStore -> {
                event.getEntity().getCapability(EyeHealthProvider.EyeHealth).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }


    @SubscribeEvent
    public static void onPlayerUnderWaterEyeDamage(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            event.player.getCapability(TimerProvider.Timer).ifPresent(timer -> {
                if(timer.getTimer() == 0) {
                    timer.setTimer(50);
                } else if (timer.getTimer() == 1){
                    event.player.getCapability(EyeHealthProvider.EyeHealth).ifPresent(eyeHealth -> {
                        if(event.player.isEyeInFluidType(Fluids.WATER.getFluidType()) == true) {
                            eyeHealth.reduceHealthValue(2.5f);
                            event.player.sendSystemMessage(Component.literal("NOOO MY EYESS "+eyeHealth.getHealthValue()));
                        } else if (event.player.isEyeInFluidType(Fluids.LAVA.getFluidType())) {
                            eyeHealth.reduceHealthValue(10f);
                        }
                    });
                } else {
                    timer.decreaseTimer();
                }

            });
        }
    }




}
