package com.example.learnclient;

import java.util.LinkedHashMap;
import java.util.Map;

/** Имена клавиш <-> коды GLFW. */
public final class Keys {
    private static final Map<String, Integer> BY_NAME = new LinkedHashMap<>();
    private static final Map<Integer, String> BY_CODE = new LinkedHashMap<>();

    private static void put(String name, int code) { BY_NAME.put(name, code); BY_CODE.putIfAbsent(code, name); }

    static {
        for (char c = 'a'; c <= 'z'; c++) put(String.valueOf(c), c - 'a' + 65);
        for (int i = 0; i <= 9; i++) { put(String.valueOf(i), 48 + i); put("numpad" + i, 320 + i); }
        for (int i = 1; i <= 12; i++) put("f" + i, 290 + i - 1);
        put("space", 32); put("tab", 258); put("enter", 257); put("backspace", 259);
        put("insert", 260); put("delete", 261); put("home", 268); put("end", 269);
        put("pageup", 266); put("pagedown", 267);
        put("right", 262); put("left", 263); put("down", 264); put("up", 265);
        put("lshift", 340); put("rshift", 344); put("lctrl", 341); put("rctrl", 345);
        put("lalt", 342); put("ralt", 346);
        put("minus", 45); put("equals", 61); put("comma", 44); put("period", 46); put("slash", 47);
        put("semicolon", 59); put("apostrophe", 39); put("lbracket", 91); put("rbracket", 93); put("grave", 96);
    }

    private Keys() {}

    /** @return код GLFW или -1, если такой клавиши нет */
    public static int parse(String name) {
        Integer c = BY_NAME.get(name.toLowerCase());
        return c == null ? -1 : c;
    }

    public static String name(int code) {
        return code < 0 ? "none" : BY_CODE.getOrDefault(code, "key" + code);
    }
}
