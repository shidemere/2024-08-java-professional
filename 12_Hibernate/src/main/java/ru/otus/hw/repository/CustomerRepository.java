package ru.otus.hw.repository;

import ru.otus.hw.model.Customer;
import ru.otus.hw.model.Item;

import java.util.List;

public interface CustomerRepository {

    List<Item> getItemsByCustomerId(long customerId);


    void remove(Customer customer);
}
