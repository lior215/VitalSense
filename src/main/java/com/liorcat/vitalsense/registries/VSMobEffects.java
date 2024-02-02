package com.liorcat.vitalsense.registries;

import com.liorcat.vitalsense.VitalSense;
import com.liorcat.vitalsense.registries.mobeffects.Photophobia;
import com.liorcat.vitalsense.registries.mobeffects.RedEyes;
import com.liorcat.vitalsense.registries.mobeffects.TunnelVision;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VSMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, VitalSense.MOD_ID);

    public static final Supplier<MobEffect> REDEYES = EFFECTS.register("redeyes", () -> new RedEyes(MobEffectCategory.HARMFUL, 0));
    public static final Supplier<MobEffect> TUNNEL_VISION = EFFECTS.register("tunnel_vision", () -> new TunnelVision(MobEffectCategory.HARMFUL, 0));
    public static final Supplier<MobEffect> PHOTOPHOBIA = EFFECTS.register("photophobia", () -> new Photophobia(MobEffectCategory.HARMFUL, 0));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

}
