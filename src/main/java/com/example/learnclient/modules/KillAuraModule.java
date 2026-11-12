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

public class KillAuraModule extends Module {
    public KillAuraModule() { super("Aura", Category.COMBAT); }

    @Override public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null || mc.world == null || mc.interactionManager == null) return;
        if (p.getAttackCooldownProgress(0.5f) < 1.0f) return;
        Entity best = null;
        double bestDist = 36.0; // 6 блоков
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity le) || e == p || !le.isAlive()) continue;
            double d = p.squaredDistanceTo(e);
            if (d < bestDist) { bestDist = d; best = e; }
        }
        if (best != null) {
            mc.interactionManager.attackEntity(p, best);
            p.swingHand(Hand.MAIN_HAND);
        }
    }
}
