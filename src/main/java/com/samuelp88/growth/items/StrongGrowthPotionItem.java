package com.samuelp88.growth.items;

import com.samuelp88.growth.entities.GrowthPotionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

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
        if(block instanceof CropBlock) {
            level.setBlock(blockPos, blockState.setValue(CropBlock.AGE, 7), 2);
        } else if(block instanceof SaplingBlock saplingBlock) {
            if (!ForgeEventFactory.saplingGrowTree(level, new Random(), blockPos)) return;
            level.setBlock(blockPos, blockState.setValue(SaplingBlock.STAGE, 1), 4);
            saplingBlock.advanceTree((ServerLevel) level, blockPos, level.getBlockState(blockPos), new Random());
        } else if(block instanceof StemBlock) {
            level.setBlock(blockPos, blockState.setValue(StemBlock.AGE, 7), 2);
        } else {
            super.applyEffect(level, block, blockPos, blockState);
        }
    }

}
