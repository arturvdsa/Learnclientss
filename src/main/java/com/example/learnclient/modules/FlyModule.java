package com.example.learnclient.modules;

import net.minecraft.client.network.ClientPlayerEntity;

public class FlyModule extends Module {
    public FlyModule() { super("Fly", Category.MOVEMENT); }

    @Override
    public void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p == null) return;
        double vy = 0;
        if (mc.options.jumpKey.isPressed()) vy = 0.6;
        if (mc.options.sneakKey.isPressed()) vy = -0.6;
        double fwd = (mc.options.forwardKey.isPressed() ? 1 : 0) - (mc.options.backKey.isPressed() ? 1 : 0);
        double str = (mc.options.leftKey.isPressed() ? 1 : 0) - (mc.options.rightKey.isPressed() ? 1 : 0);
        double vx = 0, vz = 0;
        if (fwd != 0 || str != 0) {
            double r = Math.toRadians(p.getYaw());
            double dx = -Math.sin(r) * fwd + Math.cos(r) * str;
            double dz = Math.cos(r) * fwd + Math.sin(r) * str;
            double len = Math.sqrt(dx * dx + dz * dz);
            vx = dx / len * 0.9;
            vz = dz / len * 0.9;
        }
        p.setVelocity(vx, vy, vz);
        p.fallDistance = 0;
    }
}
