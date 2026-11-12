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

public class DeathCoordsModule extends Module {
    public DeathCoordsModule() { super("Death Coords", Category.MISC); }

    private boolean wasDead;
    @Override public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null) return;
        boolean dead = p.isDead();
        if (dead && !wasDead) {
            p.sendMessage(Text.literal(String.format("Ты умер: x %d y %d z %d", (int) p.getX(), (int) p.getY(), (int) p.getZ())), false);
        }
        wasDead = dead;
    }
}
