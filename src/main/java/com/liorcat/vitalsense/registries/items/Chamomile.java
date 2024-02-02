package com.liorcat.vitalsense.registries.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Chamomile extends Item {
    public Chamomile(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 40;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return super.getDrinkingSound();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(!pLevel.isClientSide && pUsedHand == InteractionHand.MAIN_HAND && pPlayer.getItemBySlot(EquipmentSlot.OFFHAND).is(new ItemStack(Items.WATER_BUCKET).getItem())) {
            pPlayer.startUsingItem(InteractionHand.MAIN_HAND);
        }


        return super.use(pLevel, pPlayer, pUsedHand);
    }


    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {

        /*
        pLivingEntity.getCapability(EyeHealthProvider.eHealth).ifPresent(eyeHealth -> {
            eyeHealth.addhealthValue(10);
            ModPackets.sendToPlayer(new S2CEyeHealth(eyeHealth.getHealthValue()), (ServerPlayer) pLivingEntity);
        });
        pLivingEntity.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.BUCKET));
         */
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
