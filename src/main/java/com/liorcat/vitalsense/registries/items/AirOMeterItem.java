package com.liorcat.vitalsense.registries.items;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.capabilities.VSCapabilities;
import com.liorcat.vitalsense.data.air.IAirQuality;
import com.liorcat.vitalsense.data.eyes.IEyeHealth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AirOMeterItem extends Item {
    public AirOMeterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        IAirQuality airQuality = pPlayer.getCapability(VSCapabilities.AirQuality.ENTITY);
        airQuality.setQuality(airQuality.getQuality()+1);
        VitalSense.LOGGER.debug("AirQuality: {}", airQuality.getQuality());
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
