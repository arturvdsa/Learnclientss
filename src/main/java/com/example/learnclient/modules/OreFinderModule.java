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

public class OreFinderModule extends Module {
    public OreFinderModule() { super("Ore Finder", Category.MISC); }

    @Override protected void onEnable() {
        ClientPlayerEntity p = mc.player;
        if (p == null || mc.world == null) { setEnabled(false); return; }
        BlockPos o = p.getBlockPos();
        final int R = 48;
        BlockPos[] best = new BlockPos[3];
        double[] bestD = { 1e18, 1e18, 1e18 };
        BlockPos.Mutable m = new BlockPos.Mutable();
        for (int x = -R; x <= R; x++) for (int z = -R; z <= R; z++) for (int y = -64; y <= 64; y++) {
            m.set(o.getX() + x, o.getY() + y, o.getZ() + z);
            Block b = mc.world.getBlockState(m).getBlock();
            int k;
            if (b == Blocks.DIAMOND_ORE || b == Blocks.DEEPSLATE_DIAMOND_ORE) k = 0;
            else if (b == Blocks.ANCIENT_DEBRIS) k = 1;
            else if (b == Blocks.EMERALD_ORE || b == Blocks.DEEPSLATE_EMERALD_ORE) k = 2;
            else continue;
            double d = x * x + y * y + z * z;
            if (d < bestD[k]) { bestD[k] = d; best[k] = m.toImmutable(); }
        }
        String[] names = { "Алмаз", "Древние обломки", "Изумруд" };
        boolean any = false;
        for (int k = 0; k < 3; k++) {
            if (best[k] == null) continue;
            any = true;
            p.sendMessage(Text.literal(String.format("%s: x %d y %d z %d (%d блоков)", names[k],
                    best[k].getX(), best[k].getY(), best[k].getZ(), (int) Math.sqrt(bestD[k]))), false);
        }
        if (!any) p.sendMessage(Text.literal("Поблизости ничего не нашлось"), false);
        setEnabled(false); // одноразовая кнопка
    }
}
