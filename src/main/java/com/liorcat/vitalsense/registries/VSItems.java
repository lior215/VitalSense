package com.liorcat.vitalsense.registries;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.registries.items.AirOMeterItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VSItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, VitalSense.MOD_ID);

    public static final Supplier<Item> AIR_O_METER = ITEMS.register("air_o_meter",
            () -> new AirOMeterItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
