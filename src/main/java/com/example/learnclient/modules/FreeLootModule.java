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

public class FreeLootModule extends Module {
    public FreeLootModule() { super("Free Loot", Category.MISC); }

    @Override protected void onEnable() {
        onServer(sp -> {
            sp.giveItemStack(new ItemStack(Items.NETHERITE_INGOT, 64));
            sp.giveItemStack(new ItemStack(Items.DIAMOND, 64));
            sp.giveItemStack(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 16));
            sp.giveItemStack(new ItemStack(Items.TOTEM_OF_UNDYING, 1));
        });
        setEnabled(false); // одноразовая кнопка
    }
}
