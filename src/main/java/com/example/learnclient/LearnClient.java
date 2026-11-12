package com.example.learnclient;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class LearnClient implements ClientModInitializer {
    public static final ModuleManager MODULES = new ModuleManager();
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        MODULES.init();
        System.out.println("[LearnClient] Инициализирован!");
    }

    // Этот метод вызывается каждый тик (если у тебя есть миксин для него)
    public static void onTick() {
        if (mc.player != null && mc.getWindow() != null) {
            // Берем окно и проверяем бинды
            long window = mc.getWindow().getHandle();
            // allowToggle = false, если открыт чат или экран
            boolean allowToggle = mc.currentScreen == null;
            MODULES.pollBinds(window, allowToggle);
        }
    }
}
