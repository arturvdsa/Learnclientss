package com.example.learnclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.example.learnclient.modules.*;
import com.example.learnclient.modules.Module;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        modules.add(new KillAuraModule());
        modules.add(new MultiAuraModule());
        modules.add(new ReachModule());
        modules.add(new StrengthModule());

        modules.add(new SprintModule());
        modules.add(new FlyModule());
        modules.add(new SpeedModule());
        modules.add(new StepModule());
        modules.add(new HighJumpModule());
        modules.add(new NoFallModule());
        modules.add(new MoonGravityModule());
        modules.add(new SpiderModule());
        modules.add(new WaterWalkModule());

        modules.add(new FullbrightModule());
        modules.add(new EspModule());
        modules.add(new XRayModule());

        modules.add(new AutoRespawnModule());
        modules.add(new GodModeModule());
        modules.add(new CreativeModule());
        modules.add(new FastBreakModule());
        modules.add(new NoClipModule());
        modules.add(new UnbreakableModule());
        modules.add(new MegaHealthModule());
        modules.add(new GiantModule());
        modules.add(new InvisibleModule());

        modules.add(new DeathCoordsModule());
        modules.add(new FreeLootModule());
        modules.add(new GiveXpModule());
        modules.add(new OreFinderModule());
        modules.add(new BlinkTpModule());
        modules.add(new GodGearModule());
        modules.add(new BodyguardsModule());
        modules.add(new NukeMobsModule());
        modules.add(new AlwaysDayModule());
    }

    public List<Module> getModules() { return modules; }

    public List<Module> getModules(Module.Category c) {
        List<Module> out = new ArrayList<>();
        for (Module m : modules) if (m.getCategory() == c) out.add(m);
        return out;
    }

    public Module find(String query) {
        String q = norm(query);
        if (q.isEmpty()) return null;
        for (Module m : modules) if (norm(m.getName()).equals(q)) return m;
        for (Module m : modules) if (norm(m.getName()).startsWith(q)) return m;
        return null;
    }

    private static String norm(String s) { return s.toLowerCase().replaceAll("[\\s_\\-]", ""); }

    private final Map<Integer, Boolean> down = new HashMap<>();

    public void pollBinds(long window, boolean allowToggle) {
        Map<Integer, Boolean> cur = new HashMap<>();
        for (Module m : modules) {
            int k = m.getBind();
            if (k >= 0) cur.computeIfAbsent(k, kk -> GLFW.glfwGetKey(window, kk) == GLFW.GLFW_PRESS);
        }
        
        if (allowToggle) {
            for (Module m : modules) {
                int k = m.getBind();
                if (k >= 0) {
                    boolean isPressed = cur.getOrDefault(k, false);
                    boolean wasPressed = down.getOrDefault(k, false);
                    if (isPressed && !wasPressed) {
                        m.toggle();
                    }
                }
            }
        }
        down.clear();
        down.putAll(cur);
    }

    // Пустышка, чтобы LearnClient.java не ругался
    public void tick() {
        // Если нужна логика каждый тик - пиши тут
    }
                }
