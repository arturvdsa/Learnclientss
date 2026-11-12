package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BodyguardsModule extends Module {
    public BodyguardsModule() { super("Bodyguards", Category.MISC); }

    @Override protected void onEnable() {
        String n = me();
        command("execute at " + n + " run summon iron_golem ~3 ~ ~");
        command("execute at " + n + " run summon iron_golem ~-3 ~ ~");
        command("execute at " + n + " run summon iron_golem ~ ~ ~3");
        setEnabled(false); // одноразовая кнопка: 3 железных голема рядом
    }
}
