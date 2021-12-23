package com.samuelp88.growth.entities;

import com.samuelp88.growth.handlers.RegistryHandler;
import com.samuelp88.growth.holder.ItemHolder;
import com.samuelp88.growth.items.GrowthPotionItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.stream.Stream;

public class GrowthPotionEntity extends ThrowableItemProjectile {

    public GrowthPotionEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(RegistryHandler.growthPotionEntity, livingEntityIn, worldIn);
    }

    public GrowthPotionEntity(EntityType<GrowthPotionEntity> growthPotionEntityEntityType, Level world) {
        super(growthPotionEntityEntityType, world);
    }

    public GrowthPotionEntity(Level worldIn, double x, double y, double z) {
        super(RegistryHandler.growthPotionEntity, x, y, z, worldIn);
    }

    @Override
    protected void onHit(HitResult pResult) {
        if(!this.level.isClientSide) {
            AABB axisalignedbb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
            Stream<BlockPos> blockPosStream = BlockPos.betweenClosedStream(axisalignedbb);
            this.level.levelEvent(2007, this.blockPosition(), 3593824);
            blockPosStream.forEach((BlockPos blockPosition) -> {
                BlockState blockState = this.level.getBlockState(blockPosition);
                Block block = blockState.getBlock();
                //Executes potion effect
                if(this.getItem().getItem() instanceof GrowthPotionItem growthPotion) {
                    growthPotion.applyEffect(level, block, blockPosition, blockState);
                }
            });

        }
        this.discard();
    }

    @Override
    protected Item getDefaultItem() {
        return ItemHolder.GROWTH_POTION_ITEM;
    }

}
