package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlwaysDayModule extends Module {
    public AlwaysDayModule() { super("Always Day", Category.MISC); }

    private int ticks;
    @Override public void onTick() {
        if (ticks++ % 100 == 0) { command("time set day"); command("weather clear"); }
    }
}
