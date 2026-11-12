package com.example.learnclient.modules;

import java.util.UUID;
import java.util.function.Consumer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class Module {
    public enum Category {
        COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render"), PLAYER("Player"), MISC("Misc");
        public final String title;
        Category(String title) { this.title = title; }
    }

    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final Category category;
    private boolean enabled;
    private int bind = -1; // GLFW-код клавиши, -1 = не назначена

    protected Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getBind() { return bind; }
    public void setBind(int key) { this.bind = key; }
    public boolean isEnabled() { return enabled; }
    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean value) {
        if (enabled == value) return;
        enabled = value;
        if (value) onEnable(); else onDisable();
    }

    protected void onEnable() {}
    protected void onDisable() {}
    public void onTick() {}

    /** Выполнить действие над серверным игроком. Работает ТОЛЬКО в одиночной игре (встроенный сервер). */
    protected static void onServer(Consumer<ServerPlayerEntity> action) {
        MinecraftServer server = mc.getServer();
        if (server == null || mc.player == null) return;
        UUID id = mc.player.getUuid();
        server.execute(() -> {
            ServerPlayerEntity sp = server.getPlayerManager().getPlayer(id);
            if (sp != null) action.accept(sp);
        });
    }

    /** Выполнить команду с правами оператора на встроенном сервере (только одиночная игра). */
    protected static void command(String cmd) {
        MinecraftServer server = mc.getServer();
        if (server == null) return;
        server.execute(() -> server.getCommandManager().executeWithPrefix(server.getCommandSource(), cmd));
    }

    public static void runOnServer(Consumer<ServerPlayerEntity> action) { onServer(action); }
    public static void runCommand(String cmd) { command(cmd); }
    public static String playerName() { return me(); }

    protected static String me() { return mc.player == null ? "" : mc.player.getName().getString(); }

    protected static void setAttr(LivingEntity e, RegistryEntry<EntityAttribute> attr, double value) {
        if (e == null) return;
        var inst = e.getAttributeInstance(attr);
        if (inst != null) inst.setBaseValue(value);
    }
}
