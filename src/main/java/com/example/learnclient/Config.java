package com.example.learnclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.example.learnclient.modules.Module;

import net.fabricmc.loader.api.FabricLoader;

/** Конфиги: config/learnclient/<имя>.cfg, строки вида  Имя|включён|клавиша */
public final class Config {
    private Config() {}

    private static Path dir() throws IOException {
        Path d = FabricLoader.getInstance().getConfigDir().resolve("learnclient");
        Files.createDirectories(d);
        return d;
    }

    public static String clean(String name) { return name.toLowerCase().replaceAll("[^a-z0-9_\\-]", ""); }

    public static void save(String name) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Module m : LearnClient.MODULES.getModules())
            lines.add(m.getName() + "|" + m.isEnabled() + "|" + Keys.name(m.getBind()));
        Files.write(dir().resolve(clean(name) + ".cfg"), lines, StandardCharsets.UTF_8);
    }

    /** @return false, если конфига нет */
    public static boolean load(String name, boolean applyEnabled) throws IOException {
        Path f = dir().resolve(clean(name) + ".cfg");
        if (!Files.exists(f)) return false;
        for (String line : Files.readAllLines(f, StandardCharsets.UTF_8)) {
            String[] p = line.split("\\|");
            if (p.length < 3) continue;
            Module m = LearnClient.MODULES.find(p[0]);
            if (m == null) continue;
            m.setBind(Keys.parse(p[2]));
            if (applyEnabled) m.setEnabled(Boolean.parseBoolean(p[1]));
        }
        return true;
    }

    public static boolean delete(String name) throws IOException {
        return Files.deleteIfExists(dir().resolve(clean(name) + ".cfg"));
    }

    public static List<String> list() throws IOException {
        List<String> out = new ArrayList<>();
        try (Stream<Path> s = Files.list(dir())) {
            s.map(p -> p.getFileName().toString()).filter(n -> n.endsWith(".cfg"))
                    .map(n -> n.substring(0, n.length() - 4)).sorted().forEach(out::add);
        }
        return out;
    }
}
