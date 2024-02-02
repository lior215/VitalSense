package com.liorcat.vitalsense.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class LightLevelProvider { //Server does not crash with this methods so it's all fine

    public int playerPosCheckDynamicLightLevel(Player player) {
        assert player != null;
        long dayTicks = player.level().getDayTime() % 24000;
        int lightLevelOnBlock = player.level().getBrightness(LightLayer.BLOCK, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSky = player.level().getBrightness(LightLayer.SKY, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSkyModifier = 0;
        int lightLevelOnRainModifier = 0;
        int actualLightLevel;

        if(player.level().canSeeSky(player.getOnPos().offset(0,+2,0))) {

            //Rain manager
            if (player.level().isRaining()) {
                lightLevelOnRainModifier = -3;
            } else if (player.level().isThundering()) {
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
        int lightLevelOnBlock = player.level().getBrightness(LightLayer.BLOCK, player.getOnPos().offset(0,+2,0));
        int lightLevelOnSky = player.level().getBrightness(LightLayer.SKY, player.getOnPos().offset(0,+2,0));
        int actualLightLevel;

        actualLightLevel = lightLevelOnBlock + lightLevelOnSky;



        return actualLightLevel;
    }



    //Block light checker

    public static List<BlockPos> getTargetedBlocks(int length, int width) {
        Minecraft minecraft = Minecraft.getInstance();
        List<BlockPos> targetedBlocks = new ArrayList<>();

        if (minecraft.player != null) {
            Vec3 playerPos = minecraft.player.getEyePosition(1.0f);
            Vec3 lookVector = minecraft.player.getLookAngle().scale(1.0f);

            for (int i = 1; i <= length; i++) {
                Vec3 targetPos = playerPos.add(lookVector.x * i, lookVector.y * i, lookVector.z * i);

                for (int j = -width / 2; j <= width / 2; j++) {
                    Vec3 rawBlockPos = targetPos.add(lookVector.z * j, 0, -lookVector.x * j);
                    BlockPos blockPos = new BlockPos(new Vec3i((int) rawBlockPos.x, (int) rawBlockPos.y, (int) rawBlockPos.z));
                    targetedBlocks.add(blockPos);
                }
            }
        }

        return targetedBlocks;
    }


    public static boolean isBlockInConeWithLight(List<BlockPos> blocks, int levelToCheck) {
        assert Minecraft.getInstance().level != null;
        for (int i = 0; i < blocks.size(); i++) {
            BlockState blockState = Minecraft.getInstance().level.getBlockState(blocks.get(i));
            if (blockState.getLightEmission(Minecraft.getInstance().level, blocks.get(i)) > levelToCheck) {
                return true;
            }
        }
        return false;
    }


    public static int getBlockInConeWithLight(List<BlockPos> blocks, int levelToCheck) {
        assert Minecraft.getInstance().level != null;
        for (int i = 0; i < blocks.size(); i++) {
            BlockState blockState = Minecraft.getInstance().level.getBlockState(blocks.get(i));
            if (blockState.getLightEmission(Minecraft.getInstance().level, blocks.get(i)) > levelToCheck) {
                return blockState.getLightEmission(Minecraft.getInstance().level, blocks.get(i));
            }
        }
        return 0;
    }




}
