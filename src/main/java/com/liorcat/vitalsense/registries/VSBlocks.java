package com.liorcat.vitalsense.registries;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.registries.blocks.Chamomile_Plant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VSBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, VitalSense.MOD_ID);

    public static final Supplier<Block> CHAMOMILE_PLANT = BLOCKS.register("chamomile_plant",
            () -> new Chamomile_Plant(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));


    private static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        VSItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
