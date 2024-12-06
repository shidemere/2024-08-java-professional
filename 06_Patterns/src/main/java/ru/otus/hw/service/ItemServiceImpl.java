package ru.otus.hw.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.otus.hw.dao.ItemDao;
import ru.otus.hw.dao.ItemDaoImpl;
import ru.otus.hw.model.Item;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao = new ItemDaoImpl();

    public static ItemService getInstance() {
        return new ItemServiceProxy();
    }

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item findById(long id) {
        return itemDao.findById(id);
    }
}
