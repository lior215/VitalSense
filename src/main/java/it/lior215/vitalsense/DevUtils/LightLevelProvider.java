package it.lior215.vitalsense.DevUtils;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class LightLevelProvider { //Server does not crash with this methods so it's all fine

    public int playerPosCheckDynamicLightLevel(Player player) {
        assert player != null;
        long dayTicks = player.level.getDayTime() % 24000;
        int lightLevelOnBlock = player.level.getBrightness(LightLayer.BLOCK, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSky = player.level.getBrightness(LightLayer.SKY, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSkyModifier = 0;
        int lightLevelOnRainModifier = 0;
        int actualLightLevel;

        if(player.level.canSeeSky(player.getOnPos().offset(0,+2,0))) {

            //Rain manager
            if (player.level.isRaining()) {
                lightLevelOnRainModifier = -3;
            } else if (player.level.isThundering()) {
                lightLevelOnRainModifier = -7;
            }

            //DayLight manager
            if (dayTicks >= 9000 && dayTicks < 12000) {
                lightLevelOnSkyModifier -= 5;
            } else if (dayTicks >= 12000 && dayTicks < 13000) {
                lightLevelOnSkyModifier -= 10;
            } else if (dayTicks >= 13000) {
                lightLevelOnSkyModifier -= 15;
            }
        }

        lightLevelOnSky = lightLevelOnSky + lightLevelOnRainModifier + lightLevelOnSkyModifier;

        if(lightLevelOnSky < 0) {
            lightLevelOnSky = 0;
        }

        actualLightLevel = lightLevelOnBlock + lightLevelOnSky;
        return actualLightLevel;
    }

    public int playerPosCheckRawLightLevel(Player player) {
        int lightLevelOnBlock = player.level.getBrightness(LightLayer.BLOCK, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSky = player.level.getBrightness(LightLayer.SKY, player.getOnPos().offset(0,+2,0));
        int actualLightLevel;

        actualLightLevel = lightLevelOnBlock + lightLevelOnSky;



        return actualLightLevel;
    }


}
