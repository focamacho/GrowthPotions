package com.samuelp88.growth.handlers;

import com.samuelp88.growth.brewrecipes.GrowthBrewRecipe;
import com.samuelp88.growth.Growth;
import com.samuelp88.growth.items.GrowthPotionItem;
import com.samuelp88.growth.items.StrongGrowthPotionItem;
import com.samuelp88.growth.entities.GrowthPotionEntity;
import com.samuelp88.growth.holder.ItemHolder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;


@Mod.EventBusSubscriber(modid = Growth.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

    public static EntityType<GrowthPotionEntity> growthPotionEntity = EntityType.Builder.<GrowthPotionEntity>of(GrowthPotionEntity::new, MobCategory.MISC)
            .sized(1.0f, 1.0f)
            .build(new ResourceLocation(Growth.MODID, "growthentity").toString());



    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();

        registry.register(growthPotionEntity.setRegistryName(Growth.MODID, "potiongrowth"));
    }

    @SubscribeEvent
    public static void onItemRegister(final RegistryEvent.Register<Item> event) {
        ItemHolder.init();
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(
                new GrowthPotionItem(
                        new Item.Properties()
                                .tab(Growth.CREATIVETAB)
                                .stacksTo(16),
                        GrowthPotionItem.registryName
                )
        );
        registry.register(
                new StrongGrowthPotionItem(
                        new Item.Properties()
                                .tab(Growth.CREATIVETAB)
                                .stacksTo(16),
                        StrongGrowthPotionItem.registryName
                ));
    }


    public static void registerBrewings() {
        BrewingRecipeRegistry.addRecipe(
                new GrowthBrewRecipe(
                        Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER)),
                        Ingredient.of(new ItemStack(Items.BONE_BLOCK)),
                        new ItemStack(ItemHolder.GROWTH_POTION_ITEM)
                )
        );
        BrewingRecipeRegistry.addRecipe(
                new BrewingRecipe(
                        Ingredient.of(new ItemStack(ItemHolder.GROWTH_POTION_ITEM)),
                        Ingredient.of(new ItemStack(Items.RED_MUSHROOM)),
                        new ItemStack(ItemHolder.STRONG_GROWTH_POTION_ITEM)
                )
        );
    }
}
