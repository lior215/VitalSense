package com.lior215.vitalsense.mobeffects;

import com.lior215.vitalsense.vitalsense;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {

    public static final DeferredRegister<MobEffect> MOD_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, vitalsense.MOD_ID);




    public static final RegistryObject<MobEffect> redeyes = MOD_EFFECTS.register("redeyes", () -> new RedEyes(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> glaucoma = MOD_EFFECTS.register("tunnel_vision", () -> new Glaucoma(MobEffectCategory.HARMFUL, 0));
    public static final RegistryObject<MobEffect> photophobia = MOD_EFFECTS.register("photophobia", () -> new photophobia(MobEffectCategory.HARMFUL, 0));



    public static void register(IEventBus eventBus) {
        MOD_EFFECTS.register(eventBus);
    }


}
