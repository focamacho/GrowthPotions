package com.samuelp88.growth.items;

import com.samuelp88.growth.entities.GrowthPotionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class StrongGrowthPotionItem extends GrowthPotionItem {

    public static String registryName = "strong_growth_potion";

    public StrongGrowthPotionItem(Properties properties, String name) {
        super(properties, name);
    }

    @Override
    protected GrowthPotionEntity createEntityInstance(Level worldIn, LivingEntity entityIn) {
        return new GrowthPotionEntity(worldIn, entityIn);
    }

    @Override
    public void applyEffect(Level level, Block block, BlockPos blockPos, BlockState blockState) {
        if(((block instanceof CropBlock) || block instanceof SaplingBlock)) {
            if(block instanceof CropBlock cropBlock) {
                level.setBlock(blockPos, cropBlock.getStateForAge(cropBlock.getMaxAge()), 2);
            } else {
                BlockState advancedBlockState = blockState;
                SaplingBlock sapplingBlock = (SaplingBlock) block;
                if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(level, new Random(), blockPos)) return;
                if(blockState.getValue(SaplingBlock.STAGE) == 0) {
                    level.setBlock(blockPos, blockState.cycle(SaplingBlock.STAGE), 4);
                    advancedBlockState = level.getBlockState(blockPos);
                }

                sapplingBlock.advanceTree((ServerLevel) level, blockPos, advancedBlockState, new Random());
            }
        }
    }

}
