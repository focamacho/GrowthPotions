package com.samuelp88.growth.dispenser;

import com.samuelp88.growth.entities.GrowthPotionEntity;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrowthPotionDispenseBehavior extends AbstractProjectileDispenseBehavior {

    @Override
    protected Projectile getProjectile(Level pLevel, Position pPosition, ItemStack pStack) {
        return Util.make(new GrowthPotionEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z()), (projectile) -> {
            projectile.setItem(pStack);
        });
    }

    @Override
    protected float getUncertainty() {
        return super.getUncertainty() * 0.5F;
    }

    @Override
    protected float getPower() {
        return super.getPower() * 1.25F;
    }

}
