package it.lior215.vitalsense.Event;

import it.lior215.vitalsense.vitalsense;
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
                if (player.getY() < 50 && !level.canSeeSky(player.getOnPos().offset(0,+1,0))) {
                    if (player.getAirSupply() > 0) {
                        player.setAirSupply(player.getAirSupply() - 1);
                    }
                }
            }
        }
    }
}