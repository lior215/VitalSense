package com.lior215.vitalsense.screen;

import com.lior215.vitalsense.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeTAB {

    public static final CreativeModeTab Vitalsense_tab = new CreativeModeTab("vitalsense_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.chamomile.get());
        }
    };
}
