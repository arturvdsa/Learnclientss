package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XRayModule extends Module {
    public XRayModule() { super("X-Ray", Category.RENDER); }

    public static volatile boolean active;
    private static final Map<Block, Boolean> CACHE = new ConcurrentHashMap<>();

    /** Какие блоки остаются видимыми: руды, древние обломки, сундуки, спаунеры. */
    public static boolean isVisible(Block b) {
        return CACHE.computeIfAbsent(b, k -> {
            String id = Registries.BLOCK.getId(k).getPath();
            return id.endsWith("_ore") || id.equals("ancient_debris") || id.equals("chest")
                    || id.equals("trapped_chest") || id.equals("ender_chest") || id.equals("spawner")
                    || id.equals("diamond_block") || id.equals("emerald_block") || id.equals("gold_block")
                    || id.equals("netherite_block") || id.equals("lava");
        });
    }

    private void reload() { if (mc.worldRenderer != null) mc.worldRenderer.reload(); }
    @Override protected void onEnable() { active = true; reload(); }
    @Override protected void onDisable() {
        active = false; reload();
        if (mc.player != null) mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
    @Override public void onTick() {
        if (mc.player != null)
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
    }
}
