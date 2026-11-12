package com.example.learnclient.modules;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;

public class SpiderModule extends Module {
    public SpiderModule() { super("Spider", Category.MOVEMENT); }

    @Override public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null || !p.horizontalCollision) return;
        var v = p.getVelocity();
        p.setVelocity(v.x, 0.25, v.z);
        p.fallDistance = 0;
    }
}
