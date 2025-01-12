package ru.otus.hw.repository;

import ru.otus.hw.model.Customer;
import ru.otus.hw.model.Item;

public interface ItemRepository {
    Customer findCustomersByItemId(long itemId);

    void remove(Item item);
}
