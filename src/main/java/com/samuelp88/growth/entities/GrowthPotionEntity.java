package com.samuelp88.growth.entities;

import com.samuelp88.growth.handlers.RegistryHandler;
import com.samuelp88.growth.holder.ItemHolder;
import com.samuelp88.growth.utils.GrowthPotionEffects;
import com.samuelp88.growth.utils.GrowthPotionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.stream.Stream;

public class GrowthPotionEntity extends ThrowableItemProjectile {

    private GrowthPotionEffects growthEffect;

    public GrowthPotionEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(RegistryHandler.growthPotionEntity, livingEntityIn, worldIn);
    }

    public GrowthPotionEntity(EntityType<GrowthPotionEntity> growthPotionEntityEntityType, Level world) {
        super(growthPotionEntityEntityType, world);
    }

    public GrowthPotionEntity(Level worldIn, double x, double y, double z) {
        super(RegistryHandler.growthPotionEntity, x, y, z, worldIn);
    }

    protected void getEffectByItem() {
        Item potionItem = this.getItem().getItem();
        if(potionItem == ItemHolder.GROWTH_POTION_ITEM) {
            this.growthEffect = GrowthPotionEffects.GrowthPotion;
        }
        else if(potionItem == ItemHolder.STRONG_GROWTH_POTION_ITEM) {
            this.growthEffect = GrowthPotionEffects.StrongGrowthPotion;
        }
    }

    protected void executePotionEffect(Block block, BlockPos blockPos, BlockState blockState) {
        if(growthEffect == null) return;
        switch (growthEffect) {
            case GrowthPotion:
                GrowthPotionUtils.GrowthPotionEffect(level, block, blockPos, blockState);
                break;
            case StrongGrowthPotion:
                GrowthPotionUtils.StrongGrowthPotionEffect(level, block, blockPos, blockState);
                break;
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        if(!this.level.isClientSide) {
            getEffectByItem();
            AABB axisalignedbb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
            Stream<BlockPos> blockPosStream = BlockPos.betweenClosedStream(axisalignedbb);
            this.level.levelEvent(2007, this.blockPosition(), 3593824);
            blockPosStream.forEach((BlockPos blockPosition) -> {
                BlockState blockState = this.level.getBlockState(blockPosition);
                Block block = blockState.getBlock();
                //Executes potion effect
                executePotionEffect(block, blockPosition, blockState);
            });

        }
        this.discard();
    }

    @Override
    protected Item getDefaultItem() {
        return ItemHolder.GROWTH_POTION_ITEM;
    }

}
