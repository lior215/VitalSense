package com.lior215.vitalsense.mobeffects;

import com.lior215.vitalsense.VitalSense;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {

    public static final DeferredRegister<MobEffect> MOD_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, VitalSense.MOD_ID);


    public static final RegistryObject<MobEffect> redeyes = MOD_EFFECTS.register("redeyes", () -> new RedEyes(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> tunnelVision = MOD_EFFECTS.register("tunnel_vision", () -> new TunnelVision(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> photophobia = MOD_EFFECTS.register("photophobia", () -> new Photophobia(MobEffectCategory.HARMFUL, 0));


    public static void register(IEventBus eventBus) {
        MOD_EFFECTS.register(eventBus);
    }


}
