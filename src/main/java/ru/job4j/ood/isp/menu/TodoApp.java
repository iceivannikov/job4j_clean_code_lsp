package ru.job4j.ood.isp.menu;

import ru.job4j.ood.isp.menu.util.Print;

import java.util.Optional;
import java.util.Scanner;

public class TodoApp {
    public static final String ACTIONS = """
            \n
            Выберите действие по номеру:
            1. Добавить элемент в корень
            2. Добавить элемент к родителю
            3. Вызвать действие
            4. Показать меню
            5. Выйти
            """;
    public static ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new Printer();
        Scanner sc = new Scanner(System.in);
        while (true) {
            Print.print(ACTIONS);
            String chose = sc.nextLine();
            switch (chose) {
                case "1" -> {
                    Print.print("Введите название элемента");
                    String input = sc.nextLine();
                    boolean add = menu.add(Menu.ROOT, input, DEFAULT_ACTION);
                    if (!add) {
                        Print.print("Такой элемент уже есть в меню. Элемент не добавлен.");
                    }
                }
                case "2" -> {
                    Print.print("Введите название родительского элемента");
                    String parentName = sc.nextLine();
                    Optional<Menu.MenuItemInfo> select = menu.select(parentName);
                    if (select.isEmpty()) {
                        Print.print("Такого элемента не существует");
                    } else {
                        Print.print("Введите название дочернего элемента");
                        String childName = sc.nextLine();
                        boolean add = menu.add(parentName, childName, DEFAULT_ACTION);
                        if (!add) {
                            Print.print("Такой элемент уже есть в меню. Элемент не добавлен.");
                        }
                    }
                }
                case "3" -> {
                    Print.print("Введите название элемента для выполнения действия");
                    String itemName = sc.nextLine();
                    Optional<Menu.MenuItemInfo> select = menu.select(itemName);
                    if (select.isEmpty()) {
                        Print.print("Такого элемента не существует");
                    } else {
                        select.get().getActionDelegate().delegate();
                    }
                }
                case "4" -> printer.print(menu);
                case "5" -> {
                    Print.print("Выход из программы.");
                    sc.close();
                    return;
                }
            }
        }
    }
}
