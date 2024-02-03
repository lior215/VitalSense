package com.liorcat.vitalsense;

import com.liorcat.vitalsense.data.VSAttachmentTypes;
import com.liorcat.vitalsense.registries.VSBlocks;
import com.liorcat.vitalsense.config.ModClientConfigs;
import com.liorcat.vitalsense.config.ModCommonConfigs;
import com.liorcat.vitalsense.registries.VSCreativeTab;
import com.liorcat.vitalsense.registries.effects.RenderBlinkEffectScreen;
import com.liorcat.vitalsense.registries.VSItems;
import com.liorcat.vitalsense.registries.VSMobEffects;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VitalSense.MOD_ID)
public class VitalSense {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "vitalsense";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "VitalSense" namespace

    public VitalSense(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfigs.SPEC, "vitalsense-client-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "vitalsense-common-config.toml");

        VSBlocks.BLOCKS.register(modEventBus);
        VSItems.ITEMS.register(modEventBus);
        VSMobEffects.EFFECTS.register(modEventBus);
        VSAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        VSCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModCommonConfigs.checkConfigs();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            RenderBlinkEffectScreen.checkCustomImageAndDiseases();
            ModClientConfigs.checkConfigs();

        }
    }
}
