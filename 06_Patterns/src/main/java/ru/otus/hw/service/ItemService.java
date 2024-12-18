package ru.otus.hw.service;

import ru.otus.hw.model.Item;

public interface ItemService {
    Item create(Item item);
    Item findById(long id);
}
