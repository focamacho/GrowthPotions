package com.samuelp88.growth;

import com.samuelp88.growth.config.GrowthConfig;
import com.samuelp88.growth.dispenser.GrowthPotionDispenseBehavior;
import com.samuelp88.growth.handlers.RegistryHandler;
import com.samuelp88.growth.holder.ItemHolder;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("growth")
public class Growth {

    public static final String MODID = "growth";
    public static final CreativeModeTab CREATIVETAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("growth:growth_potion")));
        }
    };

    public Growth() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GrowthConfig.CONFIG_SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doLaterStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        RegistryHandler.registerBrewings();
    }

    private void doLaterStuff(final ParallelDispatchEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ItemHolder.GROWTH_POTION_ITEM, (source, stack) -> new GrowthPotionDispenseBehavior().dispense(source, stack));
            DispenserBlock.registerBehavior(ItemHolder.STRONG_GROWTH_POTION_ITEM, (source, stack) -> new GrowthPotionDispenseBehavior().dispense(source, stack));
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {

        @SubscribeEvent
        public static void onEntityRendererRegistry(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(RegistryHandler.growthPotionEntity, ThrownItemRenderer::new);
        }

    }

}
