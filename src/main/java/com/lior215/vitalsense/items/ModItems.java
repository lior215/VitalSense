package com.lior215.vitalsense.items;

import com.lior215.vitalsense.VitalSense;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModItems {
    public static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VitalSense.MOD_ID);

    public static final RegistryObject<Item> AIR_O_METER = MOD_ITEMS.register("air_o_meter",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> DIVING_GOGGLES = MOD_ITEMS.register("diving_goggles",
            () -> new DivingGogglesItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static void register(IEventBus eventBus) {
        MOD_ITEMS.register(eventBus);
    }
}
