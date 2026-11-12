package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GiantModule extends Module {
    public GiantModule() { super("Giant", Category.PLAYER); }

    @Override protected void onEnable() { onServer(sp -> setAttr(sp, EntityAttributes.SCALE, 4.0)); }
    @Override protected void onDisable() { onServer(sp -> setAttr(sp, EntityAttributes.SCALE, 1.0)); }
}
