package ru.otus.hw.dao;

import ru.otus.hw.exception.EntityNotFoundException;
import ru.otus.hw.model.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemDaoImpl implements ItemDao {
    private final Map<Long, Item> items = new HashMap<>();
    @Override
    public Item findById(long id) {
        Item item = Optional.of(items.get(id)).orElseThrow(EntityNotFoundException::new);
        System.out.println("Чтение из БД = " + item.toString());
        return item;
    }

    @Override
    public Item create(Item item) {
        System.out.println("Сохранение в БД = " + item.toString());
        items.put(item.getId(), item);
        return item;
    }

}
