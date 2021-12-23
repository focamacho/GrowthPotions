package com.samuelp88.growth.items;

import com.samuelp88.growth.entities.GrowthPotionEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class StrongGrowthPotionItem extends GrowthPotionItem {

    public static String registryName = "strong_growth_potion";

    public StrongGrowthPotionItem(Properties properties, String name) {
        super(properties, name);
    }

    @Override
    protected GrowthPotionEntity createEntityInstance(Level worldIn, LivingEntity entityIn) {
        return new GrowthPotionEntity(worldIn, entityIn);
    }

}
