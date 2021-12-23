package com.samuelp88.growth;

import com.samuelp88.growth.entities.GrowthPotionEntity;
import com.samuelp88.growth.handlers.RegistryHandler;
import com.samuelp88.growth.holder.ItemHolder;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("growth")
public class Growth {

    // Directly reference a log4j logger.
    public static final String MODID = "growth";
    public static final CreativeModeTab CREATIVETAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("growth:growth_potion")));
        }
    };
    public static final Logger LOGGER = LogManager.getLogger();

    public Growth() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doLaterStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        RegistryHandler.registerBrewings();
    }

    private void doLaterStuff(final ParallelDispatchEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ItemHolder.GROWTH_POTION_ITEM, new DispenseItemBehavior() {
                public ItemStack dispense(BlockSource p_dispense_1_, ItemStack p_dispense_2_) {
                    return (new AbstractProjectileDispenseBehavior() {
                        /**
                         * Return the projectile entity spawned by this dispense behavior.
                         */
                        protected Projectile getProjectile(Level pLevel, Position pPosition, ItemStack pStack) {
                            return Util.make(new GrowthPotionEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z()), (p_218411_1_) -> {
                                p_218411_1_.setItem(pStack);
                            });
                        }

                        protected float getUncertainty() {
                            return super.getUncertainty() * 0.5F;
                        }

                        protected float getPower() {
                            return super.getPower() * 1.25F;
                        }
                    }).dispense(p_dispense_1_, p_dispense_2_);
                }
            });

            DispenserBlock.registerBehavior(ItemHolder.STRONG_GROWTH_POTION_ITEM, new DispenseItemBehavior() {
                @Override
                public ItemStack dispense(BlockSource p_dispense_1_, ItemStack p_dispense_2_) {
                    return (new AbstractProjectileDispenseBehavior() {
                        /**
                         * Return the projectile entity spawned by this dispense behavior.
                         */
                        protected Projectile getProjectile(Level pLevel, Position pPosition, ItemStack pStack) {
                            return Util.make(new GrowthPotionEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z()), (p_218411_1_) -> {
                                p_218411_1_.setItem(pStack);
                            });
                        }

                        protected float getUncertainty() {
                            return super.getUncertainty() * 0.5F;
                        }

                        protected float getPower() {
                            return super.getPower() * 1.25F;
                        }
                    }).dispense(p_dispense_1_, p_dispense_2_);
                }
            });
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onEntityRendererRegistry(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(RegistryHandler.growthPotionEntity, ThrownItemRenderer::new);
        }

    }

}
