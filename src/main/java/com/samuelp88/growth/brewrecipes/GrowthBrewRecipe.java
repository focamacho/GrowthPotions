package com.samuelp88.growth.brewrecipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;

public class GrowthBrewRecipe extends BrewingRecipe {
    public GrowthBrewRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        super(input, ingredient, output);
    }

    @Override
    public boolean isInput(@Nonnull ItemStack stack)
    {
        ItemStack potion = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER);
        return ItemStack.tagMatches(stack, potion);
    }

}
