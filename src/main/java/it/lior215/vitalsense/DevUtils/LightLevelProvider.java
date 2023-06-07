package it.lior215.vitalsense.DevUtils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class LightLevelProvider { //might make server crash needs testing, also for now the methods in this class are not working, trying to fix it


    public int playerPoscheckDynamicLightLevel(Player player) {
        int lightLevelOnBlock = player.level.getBrightness(LightLayer.BLOCK, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSky = player.level.getBrightness(LightLayer.SKY, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSkyModifier = 0;
        int actualLightLevel;

        if(player.level.isRaining()) {
            lightLevelOnSkyModifier = 6;
        } else if (player.level.isNight()) {
            lightLevelOnSkyModifier = 15;
        }

        if(lightLevelOnSky - lightLevelOnSkyModifier < 0) {
            lightLevelOnSky = 0;
        }

        actualLightLevel = lightLevelOnBlock + lightLevelOnSky - lightLevelOnSkyModifier;
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
