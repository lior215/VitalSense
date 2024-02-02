package com.liorcat.vitalsense.registries.items;

import com.liorcat.vitalsense.utils.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class DivingGogglesItem extends ArmorItem {
    public DivingGogglesItem(Properties pProperties) {
        super(ModArmorMaterials.PLASTIC, Type.HELMET, pProperties);
    }
}
