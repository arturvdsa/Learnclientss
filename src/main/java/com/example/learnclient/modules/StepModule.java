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

public class StepModule extends Module {
    public StepModule() { super("Step", Category.MOVEMENT); }

    private void setHeight(double h) { setAttr(mc.player, EntityAttributes.STEP_HEIGHT, h); }
    @Override protected void onEnable() { setHeight(1.25); }
    @Override protected void onDisable() { setHeight(0.6); }
}
