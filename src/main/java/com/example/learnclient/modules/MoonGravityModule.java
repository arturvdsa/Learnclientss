package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MoonGravityModule extends Module {
    public MoonGravityModule() { super("Moon Gravity", Category.MOVEMENT); }

    @Override protected void onEnable() { setAttr(mc.player, EntityAttributes.GRAVITY, 0.02); }
    @Override protected void onDisable() { setAttr(mc.player, EntityAttributes.GRAVITY, 0.08); }
}
