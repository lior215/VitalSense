package com.liorcat.vitalsense.registries;

import com.liorcat.vitalsense.VitalSense;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VSCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VitalSense.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.vitalsense")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> VSItems.AIR_O_METER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(VSItems.AIR_O_METER.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
}
