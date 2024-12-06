package ru.otus.hw.service;

import ru.otus.hw.config.CustomDataSource;
import ru.otus.hw.model.Item;

/*
 * Мне изначально казалось, что неправильно создавать прокси, который имплементирует тот же интерфейс.
 * Но потом я вспомнил, что Spring по такому же методу работает, поэтому это не должно быть страшным.
 */
public class ItemServiceProxy implements ItemService {
    private ItemServiceImpl itemService;
    @Override
    public Item create(Item item) {
        if (itemService == null) {
            itemService = new ItemServiceImpl();
        }
        CustomDataSource.getInstance().beginTransaction();
        Item response = itemService.create(item);
        CustomDataSource.getInstance().closeTransaction();
        return response;
    }

    @Override
    public Item findById(long id) {
        if (itemService == null) {
            itemService = new ItemServiceImpl();
        }
        CustomDataSource.getInstance().beginTransaction();
        Item response = itemService.findById(id);
        CustomDataSource.getInstance().closeTransaction();
        return response;
    }
}
