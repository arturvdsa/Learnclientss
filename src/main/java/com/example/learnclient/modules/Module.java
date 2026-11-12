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

    protected void onEnable() {}
    protected void onDisable() {}
    public void onTick() {}

    protected static void onServer(Consumer<ServerPlayerEntity> action) {
        MinecraftServer server = mc.getServer();
        if (server == null || mc.player == null) return;
        UUID id = mc.player.getUuid();
        server.execute(() -> {
            ServerPlayerEntity sp = server.getPlayerManager().getPlayer(id);
            if (sp != null) action.accept(sp);
        });
    }

    // --- ЗАКОММЕНТИРОВАНО, ЧТОБЫ НЕ БЫЛО ОШИБКИ COMPILE ---
    /*
    protected void executeCommand(String cmd) {
        MinecraftServer server = mc.getServer();
        if (server != null) {
            server.execute(() -> server.getCommandManager().executeWithPrefix(server.getCommandSource(), cmd));
        }
    }
    */
}
