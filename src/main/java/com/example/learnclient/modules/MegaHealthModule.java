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

public class MegaHealthModule extends Module {
    public MegaHealthModule() { super("Mega Health", Category.PLAYER); }

    @Override protected void onEnable() {
        onServer(sp -> { setAttr(sp, EntityAttributes.MAX_HEALTH, 200.0); sp.setHealth(200.0f); });
    }
    @Override protected void onDisable() {
        onServer(sp -> { setAttr(sp, EntityAttributes.MAX_HEALTH, 20.0); sp.setHealth(Math.min(sp.getHealth(), 20.0f)); });
    }
}
