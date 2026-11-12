package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NukeMobsModule extends Module {
    public NukeMobsModule() { super("Nuke Mobs", Category.MISC); }

    @Override protected void onEnable() {
        command("execute at " + me() + " run kill @e[type=!player,distance=..80]");
        setEnabled(false); // одноразовая кнопка: убивает всё живое в 80 блоках
    }
}
