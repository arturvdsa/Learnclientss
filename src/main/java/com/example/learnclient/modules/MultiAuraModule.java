package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

public class MultiAuraModule extends Module {
    public MultiAuraModule() { super("Multi Aura", Category.COMBAT); }

    @Override public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null || mc.world == null || mc.interactionManager == null) return;
        int hits = 0;
        for (Entity e : mc.world.getEntities()) {
            if (hits >= 10) break;
            if (!(e instanceof LivingEntity le) || e == p || !le.isAlive()) continue;
            if (p.squaredDistanceTo(e) > 64.0) continue; // 8 блоков
            mc.interactionManager.attackEntity(p, e);
            hits++;
        }
        if (hits > 0) p.swingHand(Hand.MAIN_HAND);
    }
}
