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

public class NoFallModule extends Module {
    public NoFallModule() { super("No Fall", Category.MOVEMENT); }

    @Override protected void onEnable() { onServer(sp -> setAttr(sp, EntityAttributes.SAFE_FALL_DISTANCE, 1024.0)); }
    @Override protected void onDisable() { onServer(sp -> setAttr(sp, EntityAttributes.SAFE_FALL_DISTANCE, 3.0)); }
}
