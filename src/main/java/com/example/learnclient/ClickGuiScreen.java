package com.example.learnclient;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.example.learnclient.modules.Module;
import com.example.learnclient.modules.Module.Category;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/** ClickGUI. Ввод читается напрямую через GLFW, поэтому не зависит от смен сигнатур событий Screen. */
public class ClickGuiScreen extends Screen {
    private static final int W = 124, GAP = 6, HEADER_H = 20, ROW_H = 15;
    private static final int PANEL_TOP = 0xE0C80A0A, PANEL_BOTTOM = 0xDFC41010;
    private static final int HEADER_TOP = 0xF0E81818, HEADER_BOTTOM = 0xEFDE1010;
    private static final int ROW_ACTIVE = 0x30FFFFFF, ROW_HOVER = 0x26FFFFFF;
    private static final int SWITCH_ON = 0xFFFF3B3B, SWITCH_OFF = 0xFF5A2A2F;
    private static final int TEXT_ON = 0xFFFFFFFF, TEXT_OFF = 0xFFE0C8C8;

    private static class Panel { int x, y; boolean open = true; }

    private final Map<Category, Panel> panels = new EnumMap<>(Category.class);
    private boolean laidOut;
    private boolean prevLeft, prevRight, prevShift;
    private final boolean[] prevKeys = new boolean[349];
    private Category dragging;
    private int dragDx, dragDy;
    private final StringBuilder search = new StringBuilder();

    public ClickGuiScreen() {
        super(Text.literal("LearnClient"));
        for (Category c : Category.values()) panels.put(c, new Panel());
        long win = client().getWindow().getHandle();
        prevLeft = GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        prevRight = GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        prevShift = GLFW.glfwGetKey(win, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
        for (int k = 65; k <= 90; k++) prevKeys[k] = GLFW.glfwGetKey(win, k) == GLFW.GLFW_PRESS;
        prevKeys[32] = GLFW.glfwGetKey(win, 32) == GLFW.GLFW_PRESS;
    }

    private static net.minecraft.client.MinecraftClient client() { return net.minecraft.client.MinecraftClient.getInstance(); }

    @Override
    protected void init() {
        if (laidOut) return;
        laidOut = true;
        int n = Category.values().length;
        int total = n * W + (n - 1) * GAP;
        int x = Math.max(GAP, (width - total) / 2);
        int y = Math.max(10, height / 6);
        for (Category c : Category.values()) {
            Panel p = panels.get(c);
            p.x = x; p.y = y;
            x += W + GAP;
        }
    }

    @Override
    public boolean shouldPause() { return false; }

    private boolean prevBackspace;

    private void handleTyping(long win) {
        for (int k = 65; k <= 90; k++) {
            boolean now = GLFW.glfwGetKey(win, k) == GLFW.GLFW_PRESS;
            if (now && !prevKeys[k] && search.length() < 20) search.append((char) (k + 32));
            prevKeys[k] = now;
        }
        boolean space = GLFW.glfwGetKey(win, 32) == GLFW.GLFW_PRESS;
        if (space && !prevKeys[32] && search.length() < 20) search.append(' ');
        prevKeys[32] = space;
        boolean bs = GLFW.glfwGetKey(win, 259) == GLFW.GLFW_PRESS;
        if (bs && !prevBackspace && search.length() > 0) search.deleteCharAt(search.length() - 1);
        prevBackspace = bs;
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        long win = client().getWindow().getHandle();
        boolean left = GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean right = GLFW.glfwGetMouseButton(win, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        boolean shift = GLFW.glfwGetKey(win, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
        if (shift && !prevShift) { close(); return; }
        prevShift = shift;
        handleTyping(win);

        boolean lClick = left && !prevLeft, rClick = right && !prevRight;
        prevLeft = left; prevRight = right;

        ctx.fill(0, 0, width, height, 0x70000000);

        if (dragging != null) {
            if (left) { Panel p = panels.get(dragging); p.x = mx - dragDx; p.y = my - dragDy; }
            else dragging = null;
        }

        String q = search.toString().trim().toLowerCase();
        for (Category c : Category.values()) {
            Panel p = panels.get(c);
            List<Module> mods = new ArrayList<>();
            for (Module m : LearnClient.MODULES.getModules(c)) {
                if (q.isEmpty() || m.getName().toLowerCase().contains(q)) mods.add(m);
            }
            int bodyH = p.open ? mods.size() * ROW_H + (mods.isEmpty() ? 0 : 3) : 0;

            if (bodyH > 0) roundedGradient(ctx, p.x, p.y + HEADER_H - 4, W, bodyH + 4, PANEL_TOP, PANEL_BOTTOM);
            roundedGradient(ctx, p.x, p.y, W, HEADER_H, HEADER_TOP, HEADER_BOTTOM);
            ctx.drawTextWithShadow(textRenderer, c.title, p.x + 8, p.y + 6, TEXT_ON);

            if (inside(mx, my, p.x, p.y, W, HEADER_H)) {
                if (lClick && dragging == null) { dragging = c; dragDx = mx - p.x; dragDy = my - p.y; }
                if (rClick) p.open = !p.open;
            }

            if (!p.open) continue;
            int ry = p.y + HEADER_H;
            for (Module m : mods) {
                boolean hover = inside(mx, my, p.x, ry, W, ROW_H);
                if (hover && lClick && dragging == null) m.toggle();
                if (m.isEnabled()) ctx.fill(p.x + 2, ry, p.x + W - 2, ry + ROW_H, ROW_ACTIVE);
                else if (hover) ctx.fill(p.x + 2, ry, p.x + W - 2, ry + ROW_H, ROW_HOVER);
                ctx.drawTextWithShadow(textRenderer, m.getName(), p.x + 8, ry + 4, m.isEnabled() ? TEXT_ON : TEXT_OFF);
                drawSwitch(ctx, p.x + W - 28, ry + 4, m.isEnabled());
                ry += ROW_H;
            }
        }

        // строка поиска
        String shown = search.length() == 0 ? "Поиск по модулям" : search.toString();
        int sw = Math.max(120, textRenderer.getWidth(shown) + 16);
        int sx = (width - sw) / 2, sy = height - 48;
        rounded(ctx, sx, sy, sw, 16, 0xB0000000);
        ctx.drawTextWithShadow(textRenderer, shown, sx + 8, sy + 4, search.length() == 0 ? 0xFF999999 : 0xFFFFFFFF);
    }

    private void drawSwitch(DrawContext ctx, int x, int y, boolean on) {
        rounded(ctx, x, y, 20, 8, on ? SWITCH_ON : SWITCH_OFF);
        int kx = on ? x + 13 : x + 1;
        ctx.fill(kx, y + 1, kx + 6, y + 7, 0xFFFFFFFF);
    }

    private static void rounded(DrawContext ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x + 1, y, x + w - 1, y + h, color);
        ctx.fill(x, y + 1, x + 1, y + h - 1, color);
        ctx.fill(x + w - 1, y + 1, x + w, y + h - 1, color);
    }

    private static void roundedGradient(DrawContext ctx, int x, int y, int w, int h, int top, int bottom) {
        ctx.fillGradient(x + 1, y, x + w - 1, y + h, top, bottom);
        ctx.fillGradient(x, y + 1, x + 1, y + h - 1, top, bottom);
        ctx.fillGradient(x + w - 1, y + 1, x + w, y + h - 1, top, bottom);
    }

    private static boolean inside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }
}
