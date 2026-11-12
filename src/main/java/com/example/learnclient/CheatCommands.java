package com.example.learnclient;

import java.util.List;

import com.example.learnclient.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

/** Чат-команды с префиксом "." — как в известных клиентах. */
public final class CheatCommands {
    public static final String PREFIX = ".";
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private CheatCommands() {}

    /** @return true, если сообщение было командой (и в чат его отправлять не надо) */
    public static boolean handle(String msg) {
        if (!msg.startsWith(PREFIX) || msg.length() < 2 || msg.startsWith("..")) return false;
        String[] a = msg.substring(1).trim().split("\\s+");
        try {
            run(a[0].toLowerCase(), a);
        } catch (Exception e) {
            info("Ошибка: " + e.getMessage());
        }
        return true;
    }

    private static void info(String text) {
        if (mc.player == null) return;
        mc.player.sendMessage(Text.literal("[LearnClient] ").formatted(Formatting.RED)
                .append(Text.literal(text).formatted(Formatting.WHITE)), false);
    }

    private static Module need(String[] a, int i) {
        if (a.length <= i) throw new IllegalArgumentException("не указан модуль");
        Module m = LearnClient.MODULES.find(a[i]);
        if (m == null) throw new IllegalArgumentException("нет модуля \"" + a[i] + "\" (.modules — список)");
        return m;
    }

    private static String arg(String[] a, int i, String what) {
        if (a.length <= i) throw new IllegalArgumentException("не хватает аргумента: " + what);
        return a[i];
    }

    private static double coord(String s, double cur) {
        if (s.startsWith("~")) return cur + (s.length() == 1 ? 0 : Double.parseDouble(s.substring(1)));
        return Double.parseDouble(s);
    }

    private static void run(String cmd, String[] a) throws Exception {
        switch (cmd) {
            case "help", "h", "?" -> help();

            case "toggle", "t" -> { Module m = need(a, 1); m.toggle(); info(m.getName() + ": " + (m.isEnabled() ? "ON" : "OFF")); }
            case "on", "enable" -> { Module m = need(a, 1); m.setEnabled(true); info(m.getName() + ": ON"); }
            case "off", "disable" -> { Module m = need(a, 1); m.setEnabled(false); info(m.getName() + ": OFF"); }

            case "modules", "list", "m" -> {
                for (Module.Category c : Module.Category.values()) {
                    StringBuilder sb = new StringBuilder(c.title).append(": ");
                    for (Module m : LearnClient.MODULES.getModules(c))
                        sb.append(m.isEnabled() ? "[" + m.getName() + "] " : m.getName() + " ");
                    info(sb.toString().trim());
                }
            }
            case "enabled", "active" -> {
                StringBuilder sb = new StringBuilder();
                for (Module m : LearnClient.MODULES.getModules()) if (m.isEnabled()) sb.append(m.getName()).append(", ");
                info(sb.length() == 0 ? "ничего не включено" : sb.substring(0, sb.length() - 2));
            }
            case "panic" -> {
                for (Module m : LearnClient.MODULES.getModules()) m.setEnabled(false);
                info("Все модули выключены");
            }

            case "bind" -> {
                if (a.length >= 2 && a[1].equalsIgnoreCase("clear")) {
                    for (Module m : LearnClient.MODULES.getModules()) m.setBind(-1);
                    info("Все бинды сброшены");
                    return;
                }
                Module m = need(a, 1);
                int key = Keys.parse(arg(a, 2, "клавиша"));
                if (key < 0) throw new IllegalArgumentException("неизвестная клавиша \"" + a[2] + "\" (пример: r, f4, rshift, numpad1)");
                m.setBind(key);
                info(m.getName() + " -> " + Keys.name(key).toUpperCase());
            }
            case "unbind" -> { Module m = need(a, 1); m.setBind(-1); info(m.getName() + ": бинд снят"); }
            case "binds" -> {
                StringBuilder sb = new StringBuilder();
                for (Module m : LearnClient.MODULES.getModules())
                    if (m.getBind() >= 0) sb.append(m.getName()).append("=").append(Keys.name(m.getBind()).toUpperCase()).append("  ");
                info(sb.length() == 0 ? "биндов нет" : sb.toString().trim());
            }

            case "cfg", "config" -> {
                String sub = arg(a, 1, "save/load/list/delete").toLowerCase();
                switch (sub) {
                    case "save" -> { Config.save(arg(a, 2, "имя")); info("Конфиг сохранён: " + Config.clean(a[2])); }
                    case "load" -> info(Config.load(arg(a, 2, "имя"), true) ? "Конфиг загружен: " + Config.clean(a[2]) : "Нет такого конфига");
                    case "delete", "remove" -> info(Config.delete(arg(a, 2, "имя")) ? "Конфиг удалён" : "Нет такого конфига");
                    case "list" -> { List<String> l = Config.list(); info(l.isEmpty() ? "конфигов нет" : String.join(", ", l)); }
                    default -> info("Использование: .cfg save|load|delete <имя> / .cfg list");
                }
            }

            // ---- одиночная игра ----
            case "gm", "gamemode" -> {
                GameMode gm = switch (arg(a, 1, "survival|creative|adventure|spectator").toLowerCase()) {
                    case "0", "s", "survival" -> GameMode.SURVIVAL;
                    case "1", "c", "creative" -> GameMode.CREATIVE;
                    case "2", "a", "adventure" -> GameMode.ADVENTURE;
                    case "3", "sp", "spectator" -> GameMode.SPECTATOR;
                    default -> throw new IllegalArgumentException("режим: survival, creative, adventure, spectator");
                };
                Module.runOnServer(sp -> sp.changeGameMode(gm));
                info("Режим: " + gm.getId());
            }
            case "tp" -> {
                if (mc.player == null) return;
                double x = coord(arg(a, 1, "x"), mc.player.getX());
                double y = coord(arg(a, 2, "y"), mc.player.getY());
                double z = coord(arg(a, 3, "z"), mc.player.getZ());
                Module.runOnServer(sp -> sp.requestTeleport(x, y, z));
                info(String.format("TP: %.1f %.1f %.1f", x, y, z));
            }
            case "vclip" -> {
                double d = Double.parseDouble(arg(a, 1, "блоки"));
                Module.runOnServer(sp -> sp.requestTeleport(sp.getX(), sp.getY() + d, sp.getZ()));
                info("vclip " + d);
            }
            case "coords", "pos" -> {
                if (mc.player != null) info(String.format("x %.1f y %.1f z %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ()));
            }
            case "heal" -> {
                Module.runOnServer(sp -> {
                    sp.setHealth(sp.getMaxHealth());
                    sp.getHungerManager().setFoodLevel(20);
                    sp.getHungerManager().setSaturationLevel(20f);
                });
                info("Вылечен");
            }
            case "xp" -> {
                int lv = Integer.parseInt(arg(a, 1, "уровни"));
                Module.runOnServer(sp -> sp.addExperienceLevels(lv));
                info("+" + lv + " уровней");
            }
            case "give" -> {
                String item = arg(a, 1, "предмет");
                String count = a.length > 2 ? a[2] : "1";
                Module.runCommand("give " + Module.playerName() + " " + item + " " + count);
                info("give " + item + " x" + count);
            }
            case "time" -> {
                String t = arg(a, 1, "day|night|noon|midnight|число");
                Module.runCommand("time set " + t);
                info("time " + t);
            }
            case "weather" -> {
                String w = arg(a, 1, "clear|rain|thunder");
                Module.runCommand("weather " + w);
                info("weather " + w);
            }
            case "kill" -> {
                int r = a.length > 1 ? Integer.parseInt(a[1]) : 50;
                Module.runCommand("execute at " + Module.playerName() + " run kill @e[type=!player,distance=.." + r + "]");
                info("kill в радиусе " + r);
            }
            case "say" -> {
                if (a.length < 2 || mc.getNetworkHandler() == null) return;
                mc.getNetworkHandler().sendChatMessage(String.join(" ", java.util.Arrays.copyOfRange(a, 1, a.length)));
            }
            default -> info("Неизвестная команда. .help — список");
        }
    }

    private static void help() {
        info("Модули: .toggle|.t <м>, .on <м>, .off <м>, .modules, .enabled, .panic");
        info("Бинды: .bind <м> <клавиша>, .unbind <м>, .binds, .bind clear");
        info("Конфиги: .cfg save|load|delete <имя>, .cfg list");
        info("Одиночка: .gm <режим>, .tp x y z (можно ~), .vclip <n>, .heal, .xp <n>, .give <предмет> [кол-во], .time <..>, .weather <..>, .kill [радиус], .coords");
        info("Прочее: .say <текст> — отправить сообщение в чат как есть");
    }
}
