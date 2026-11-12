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

public class SprintModule extends Module {
    public SprintModule() { super("Sprint", Category.MOVEMENT); }

    @Override public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null) return;
        if (mc.options.forwardKey.isPressed() && !p.isSneaking() && !p.horizontalCollision) p.setSprinting(true);
    }
}
