package com.example.learnclient;

import java.util.ArrayList;
import java.util.List;

import com.example.learnclient.modules.Module;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class LearnClient implements ClientModInitializer {
    public static final ModuleManager MODULES = new ModuleManager();
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        MODULES.init();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.learnclient.gui", InputUtil.Type.KEYSYM, 344 /* Right Shift */,
                KeyBinding.Category.create(Identifier.of("learnclient", "main"))));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            if (openGuiKey.wasPressed() && mc.currentScreen == null && mc.player != null) {
                mc.setScreen(new ClickGuiScreen());
            }
            MODULES.pollBinds(mc.getWindow().getHandle(), mc.currentScreen == null && mc.player != null);
            if (mc.player != null) MODULES.tick();
        });

        // Чат-команды: ".help", ".bind", ".cfg" ...
        ClientSendMessageEvents.ALLOW_CHAT.register(msg -> !CheatCommands.handle(msg));

        // Бинды переживают перезапуск: автозагрузка/автосохранение конфига "default" (только клавиши)
        try { Config.load("default", false); } catch (Exception ignored) {}
        ClientLifecycleEvents.CLIENT_STOPPING.register(mc -> { try { Config.save("default"); } catch (Exception ignored) {} });

        // Список включённых модулей справа сверху
        HudRenderCallback.EVENT.register((ctx, tickCounter) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null || mc.options.hudHidden) return;
            List<Module> on = new ArrayList<>();
            for (Module m : MODULES.getModules()) if (m.isEnabled()) on.add(m);
            on.sort((a, b) -> Integer.compare(mc.textRenderer.getWidth(b.getName()), mc.textRenderer.getWidth(a.getName())));
            int right = ctx.getScaledWindowWidth();
            int y = 4;
            for (Module m : on) {
                int w = mc.textRenderer.getWidth(m.getName());
                int x = right - w - 6;
                ctx.fill(x - 3, y - 1, right, y + 10, 0x90000000);
                ctx.fill(right - 2, y - 1, right, y + 10, 0xFF8F8FFF);
                ctx.drawTextWithShadow(mc.textRenderer, m.getName(), x, y, 0xFF8F8FFF);
                y += 11;
            }
        });
    }
}
