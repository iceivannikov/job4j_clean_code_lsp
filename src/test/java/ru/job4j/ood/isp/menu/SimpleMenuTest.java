package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.isp.menu.util.Print;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    void whenAddDuplicateElementThenIgnore() {
        Menu menu = new SimpleMenu();
        boolean firstAdd = menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        boolean secondAdd = menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        assertThat(firstAdd).isTrue();
        assertThat(secondAdd).isFalse();
    }

    @Test
    void whenSelectExistingElementThenReturnMenuItemInfo() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        Optional<Menu.MenuItemInfo> select = menu.select("Купить хлеб");
        assertThat(select.isPresent()).isTrue();
        assertThat(select.get().getNumber()).isEqualTo("1.1.1.");
        assertThat(select.get().getName()).isEqualTo("Купить хлеб");
        assertThat(select.get().getChildren()).isEmpty();
    }

    @Test
    void whenSelectNonExistingElementThenReturnEmpty() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        Optional<Menu.MenuItemInfo> select = menu.select("Купить хлеб");
        assertThat(select).isEmpty();
    }

    @Test
    void whenPrintMenuThenOutputCorrect() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        String actual = Print.menuToString(menu);
        String expected = """
                1.Сходить в магазин
                ----1.1.Купить продукты
                --------1.1.1.Купить хлеб
                --------1.1.2.Купить молоко
                2.Покормить собаку
                """;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenMenuEmptyThenOutputMenuEmpty() {
        Menu menu = new SimpleMenu();
        String actual = Print.menuToString(menu);
        String expected = "Меню пусто.";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenMenuEmptyThenNoIteration() {
        Menu menu = new SimpleMenu();
        assertThat(menu.iterator().hasNext()).isFalse();
    }
}