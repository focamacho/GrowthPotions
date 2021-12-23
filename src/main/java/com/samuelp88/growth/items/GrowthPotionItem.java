package com.samuelp88.growth.items;

import com.samuelp88.growth.config.GrowthConfig;
import com.samuelp88.growth.entities.GrowthPotionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class GrowthPotionItem extends Item {

    public static String registryName = "growth_potion";

    public GrowthPotionItem(Properties properties, String name) {
        super(properties);
        this.setRegistryName(name);
    }

    protected GrowthPotionEntity createEntityInstance(Level worldIn, LivingEntity entityIn) {
        return new GrowthPotionEntity(worldIn, entityIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!worldIn.isClientSide) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
            GrowthPotionEntity potionEntity = this.createEntityInstance(worldIn, playerIn);
            potionEntity.setItem(itemstack);
            potionEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), -20.0F, 0.5F, 1.0F);
            worldIn.addFreshEntity(potionEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            items.add(new ItemStack(this));
        }
    }


    public void applyEffect(Level level, Block block, BlockPos blockPos, BlockState blockState) {
        if((block instanceof BonemealableBlock growableBlock) && !(block instanceof GrassBlock)) {
            if(!growableBlock.isBonemealSuccess(level, level.random, blockPos, blockState) && !GrowthConfig.ignoreBonemealable.get()) return;
            growableBlock.performBonemeal((ServerLevel) level, new Random(), blockPos, blockState);
        }
    }

}
