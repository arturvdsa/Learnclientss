package com.example.learnclient.modules;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;

public class ReachModule extends Module {
    public ReachModule() { super("Reach", Category.COMBAT); }

    private void apply(double entity, double block) {
        setAttr(mc.player, EntityAttributes.ENTITY_INTERACTION_RANGE, entity);
        setAttr(mc.player, EntityAttributes.BLOCK_INTERACTION_RANGE, block);
        onServer(sp -> {
            setAttr(sp, EntityAttributes.ENTITY_INTERACTION_RANGE, entity);
            setAttr(sp, EntityAttributes.BLOCK_INTERACTION_RANGE, block);
        });
    }
    @Override protected void onEnable() { apply(6.0, 8.0); }
    @Override protected void onDisable() { apply(3.0, 4.5); }
}
