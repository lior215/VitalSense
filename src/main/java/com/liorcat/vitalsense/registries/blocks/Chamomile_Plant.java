package com.liorcat.vitalsense.registries.blocks;

import com.liorcat.vitalsense.registries.VSItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class Chamomile_Plant extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age",0,4);

    public Chamomile_Plant(Properties pProperties) {
        super(pProperties);
    }

    /*
    protected ItemLike getBaseSeedId() {
        return VSItems.chamomile_seeds.get();
    }
     */

    public IntegerProperty getAgeProperty() { //override degli state dei crop vanilla
        return AGE;
    }

    public int getMaxAge() { //override degli state dei crop vanilla
        return 4;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) { //override degli state dei crop vanilla
        pBuilder.add(AGE);
    }


}
