package com.samuelp88.growth.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class GrowthPotionUtils {

    public static void GrowthPotionEffect(Level level, Block block, BlockPos blockPos, BlockState blockState) {
        if((block instanceof BonemealableBlock) && !(block instanceof GrassBlock)) {
            BonemealableBlock growableBlock = (BonemealableBlock) block;
            growableBlock.performBonemeal((ServerLevel) level, new Random(), blockPos, blockState);
        }
    }

    public static void StrongGrowthPotionEffect(Level level, Block block, BlockPos blockPos, BlockState blockState) {
        if(((block instanceof CropBlock) || block instanceof SaplingBlock)) {
            if(block instanceof CropBlock) {
                CropBlock cropBlock = (CropBlock) block;
                level.setBlock(blockPos, cropBlock.getStateForAge(cropBlock.getMaxAge()), 2);
            }
            else {
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
