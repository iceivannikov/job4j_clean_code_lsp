package ru.job4j.ood.isp.menu.util;

import ru.job4j.ood.isp.menu.Menu;

public class Print {
    private Print() {
    }
    public static String menuToString(Menu menu) {
        StringBuilder result = new StringBuilder();
        if (!menu.iterator().hasNext()) {
            result.append("Меню пусто.");
        } else {
            for (Menu.MenuItemInfo menuItemInfo : menu) {
                int level = (int) menuItemInfo.getNumber().chars().filter(ch -> ch == '.').count() - 1;
                String repeat = "----".repeat(level);
                result.append(repeat)
                        .append(menuItemInfo.getNumber())
                        .append(menuItemInfo.getName())
                        .append("\n");
            }
        }
        return result.toString();
    }
    public static void print(String text) {
        System.out.println(text);
    }
}
