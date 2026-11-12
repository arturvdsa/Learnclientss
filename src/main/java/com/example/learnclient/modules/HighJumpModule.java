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

public class HighJumpModule extends Module {
    public HighJumpModule() { super("High Jump", Category.MOVEMENT); }

    @Override protected void onEnable() { setAttr(mc.player, EntityAttributes.JUMP_STRENGTH, 1.0); }
    @Override protected void onDisable() { setAttr(mc.player, EntityAttributes.JUMP_STRENGTH, 0.42); }
}
