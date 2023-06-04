package it.lior215.vitalsense.Event;

import it.lior215.vitalsense.vitalsense;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = vitalsense.MOD_ID, value = Dist.CLIENT)
public class ModBreathingEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (player != null) {
                Level level = player.level;
                if (player.getY() < 100 && canSeeSky(player, level)) {
                    player.sendSystemMessage(Component.literal(String.valueOf(level.getHeight())));
                }
            }
        }
    }

    private static boolean canSeeSky(Player player, Level world) {
        BlockPos playerPos = player.getOnPos();

        if (playerPos.getY() >= world.getHeight()) {
            // Player is above the world height limit, consider it as seeing the sky
            return true;
        }

        // Check if there are any non-air blocks between the player's eye position and the sky
        for (BlockPos blockPos : BlockPos.betweenClosed(playerPos, playerPos.offset(0, world.getHeight(), 0))) {
            if (!world.getBlockState(blockPos).isAir()) {
                return false;
            }
        }

        return true;
    }
}