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

public class NoClipModule extends Module {
    public NoClipModule() { super("NoClip", Category.PLAYER); }

    // Режим наблюдателя на встроенном сервере: летаешь сквозь блоки и стены.
    @Override protected void onEnable() { onServer(sp -> sp.changeGameMode(GameMode.SPECTATOR)); }
    @Override protected void onDisable() { onServer(sp -> sp.changeGameMode(GameMode.SURVIVAL)); }
}
