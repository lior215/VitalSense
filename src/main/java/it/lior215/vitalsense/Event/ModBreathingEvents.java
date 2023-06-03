package it.lior215.vitalsense.Event;

import it.lior215.vitalsense.vitalsense;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID)
public class ModBreathingEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (event.phase == TickEvent.Phase.END) {
            if (player != null) {
                if (player.getY() < 100) {
                    /*String playerY = String.valueOf(player.getY());
                    player.sendSystemMessage(Component.literal(playerY));*/
                }
            }
        }
    }
}