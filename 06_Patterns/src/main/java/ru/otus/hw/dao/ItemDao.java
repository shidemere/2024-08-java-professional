package ru.otus.hw.dao;

import ru.otus.hw.model.Item;

public interface ItemDao {

    Item findById(long id);
    Item create(Item item);
}
