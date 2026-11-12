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

public class EspModule extends Module {
    public EspModule() { super("Entity ESP", Category.RENDER); }

    @Override public void onTick() {
        if (mc.world == null) return;
        for (Entity e : mc.world.getEntities()) if (e != mc.player) e.setGlowing(true);
    }
    @Override protected void onDisable() {
        if (mc.world == null) return;
        for (Entity e : mc.world.getEntities()) if (e != mc.player) e.setGlowing(false);
    }
}
