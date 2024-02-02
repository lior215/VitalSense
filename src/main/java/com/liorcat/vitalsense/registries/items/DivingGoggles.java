package com.liorcat.vitalsense.registries.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class DivingGoggles extends ArmorItem {

    private boolean hasDivingGoggles = false;

    public boolean getHasDivingGoggles() {
        return hasDivingGoggles;
    }

    public DivingGoggles(ArmorMaterial pMaterial, ArmorItem.Type pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);

    }


}
