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

public class UnbreakableModule extends Module {
    public UnbreakableModule() { super("Unbreakable", Category.PLAYER); }

    @Override public void onTick() {
        onServer(sp -> {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack s = sp.getEquippedStack(slot);
                if (!s.isEmpty() && s.isDamageable()) s.setDamage(0);
            }
        });
    }
}
