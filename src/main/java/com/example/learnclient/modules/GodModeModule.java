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

public class GodModeModule extends Module {
    public GodModeModule() { super("God Mode", Category.PLAYER); }

    @Override protected void onEnable() { onServer(sp -> sp.setInvulnerable(true)); }
    @Override protected void onDisable() { onServer(sp -> sp.setInvulnerable(false)); }
    @Override public void onTick() {
        onServer(sp -> {
            sp.setHealth(sp.getMaxHealth());
            sp.getHungerManager().setFoodLevel(20);
            sp.getHungerManager().setSaturationLevel(20.0f);
            sp.setAir(sp.getMaxAir());
            sp.extinguish();
        });
    }
}
