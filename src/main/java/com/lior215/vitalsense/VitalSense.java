package com.lior215.vitalsense;

import com.lior215.vitalsense.blocks.ModBlocks;
import com.lior215.vitalsense.config.ModClientConfigs;
import com.lior215.vitalsense.config.ModCommonConfigs;
import com.lior215.vitalsense.effects.RenderBlinkEffectScreen;
import com.lior215.vitalsense.items.ModItems;
import com.lior215.vitalsense.network.ModPackets;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.lior215.vitalsense.mobeffects.ModMobEffects.MOD_EFFECTS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VitalSense.MOD_ID)
public class VitalSense {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "vitalsense";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "VitalSense" namespace

    public VitalSense() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfigs.SPEC, "vitalsense-client-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "vitalsense-common-config.toml");

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        MOD_EFFECTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModCommonConfigs.checkConfigs();
        ModPackets.register();
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
