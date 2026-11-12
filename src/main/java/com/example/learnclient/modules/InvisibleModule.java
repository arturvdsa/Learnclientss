package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InvisibleModule extends Module {
    public InvisibleModule() { super("Invisible", Category.PLAYER); }

    @Override public void onTick() {
        onServer(sp -> sp.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 40, 0, false, false, false)));
    }
    @Override protected void onDisable() { onServer(sp -> sp.removeStatusEffect(StatusEffects.INVISIBILITY)); }
}
