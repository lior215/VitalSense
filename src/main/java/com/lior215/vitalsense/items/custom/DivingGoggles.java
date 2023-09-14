package com.lior215.vitalsense.items.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class DivingGoggles extends ArmorItem {

    private boolean hasDivingGoggles = false;

    public boolean getHasDivingGoggles() {
        return hasDivingGoggles;
    }

    public DivingGoggles(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);

    }


}
