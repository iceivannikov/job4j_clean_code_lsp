package ru.job4j.ood.isp.menu;

import ru.job4j.ood.isp.menu.util.Print;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        System.out.println(Print.menuToString(menu));
    }
}
