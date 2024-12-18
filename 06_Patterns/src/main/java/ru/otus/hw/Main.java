package ru.otus.hw;

import ru.otus.hw.model.Item;
import ru.otus.hw.service.ItemService;
import ru.otus.hw.service.ItemServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        List<Item> items = List.of(
                new Item(1, "One", 10),
                new Item(2, "Two", 20),
                new Item(3, "Three", 30),
                new Item(4, "Four", 40),
                new Item(5, "Five", 50),
                new Item(6, "Six", 60),
                new Item(7, "Seven", 70),
                new Item(8, "Eight", 80),
                new Item(9, "Nine", 90),
                new Item(10, "Ten", 100)
        );

        ItemService service = ItemServiceImpl.getInstance();
        for (Item item : items) {
            service.create(item);
        }

        System.out.println("--------------------------------");

        List<Long> ids = items.stream().map(Item::getId).toList();
        for (long id : ids) {
            service.findById(id);
        }
    }
}