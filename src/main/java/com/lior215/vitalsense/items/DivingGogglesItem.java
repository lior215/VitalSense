package com.lior215.vitalsense.items;

import com.lior215.vitalsense.utils.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class DivingGogglesItem extends ArmorItem {
    public DivingGogglesItem(Properties pProperties) {
        super(ModArmorMaterials.PLASTIC, EquipmentSlot.HEAD, pProperties);
    }
}
