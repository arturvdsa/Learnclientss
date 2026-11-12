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

public class SpeedModule extends Module {
    public SpeedModule() { super("Speed", Category.MOVEMENT); }

    @Override public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null) return;
        double fwd = (mc.options.forwardKey.isPressed() ? 1 : 0) - (mc.options.backKey.isPressed() ? 1 : 0);
        double str = (mc.options.leftKey.isPressed() ? 1 : 0) - (mc.options.rightKey.isPressed() ? 1 : 0);
        if (fwd == 0 && str == 0) return;
        double r = Math.toRadians(p.getYaw());
        double dx = -Math.sin(r) * fwd + Math.cos(r) * str;
        double dz = Math.cos(r) * fwd + Math.sin(r) * str;
        double len = Math.sqrt(dx * dx + dz * dz);
        var v = p.getVelocity();
        p.setVelocity(dx / len * 0.45, v.y, dz / len * 0.45);
    }
}
