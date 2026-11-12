package com.example.learnclient.modules;

import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GodGearModule extends Module {
    public GodGearModule() { super("God Gear", Category.MISC); }

    @Override protected void onEnable() {
        String n = me();
        String ench = "{protection:255,unbreaking:255,mending:1}";
        command("give " + n + " netherite_sword[enchantments={sharpness:255,looting:10,fire_aspect:2,sweeping_edge:10,unbreaking:255},unbreakable={}]");
        command("give " + n + " netherite_pickaxe[enchantments={efficiency:255,fortune:10,unbreaking:255},unbreakable={}]");
        command("give " + n + " netherite_axe[enchantments={efficiency:255,sharpness:255},unbreakable={}]");
        command("give " + n + " netherite_helmet[enchantments=" + ench + ",unbreakable={}]");
        command("give " + n + " netherite_chestplate[enchantments=" + ench + ",unbreakable={}]");
        command("give " + n + " netherite_leggings[enchantments=" + ench + ",unbreakable={}]");
        command("give " + n + " netherite_boots[enchantments={protection:255,feather_falling:255,unbreaking:255},unbreakable={}]");
        command("give " + n + " elytra[unbreakable={}]");
        command("give " + n + " firework_rocket 64");
        command("give " + n + " bow[enchantments={power:255,infinity:1,flame:1},unbreakable={}]");
        command("give " + n + " arrow 64");
        setEnabled(false); // одноразовая кнопка
    }
}
