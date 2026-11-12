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

public class BlinkTpModule extends Module {
    public BlinkTpModule() { super("Blink TP", Category.MISC); }

    @Override protected void onEnable() {
        ClientPlayerEntity p = mc.player;
        if (p != null) {
            Vec3d look = p.getRotationVec(1.0f);
            double x = p.getX() + look.x * 20, y = p.getY() + look.y * 20, z = p.getZ() + look.z * 20;
            onServer(sp -> sp.requestTeleport(x, y, z));
        }
        setEnabled(false); // одноразовая кнопка: прыжок на 20 блоков вперёд
    }
}
