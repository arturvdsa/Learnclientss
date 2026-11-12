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

public class StrengthModule extends Module {
    public StrengthModule() { super("One Hit", Category.COMBAT); }

    @Override public void onTick() {
        onServer(sp -> sp.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 40, 30, false, false, false)));
    }
    @Override protected void onDisable() { onServer(sp -> sp.removeStatusEffect(StatusEffects.STRENGTH)); }
}
