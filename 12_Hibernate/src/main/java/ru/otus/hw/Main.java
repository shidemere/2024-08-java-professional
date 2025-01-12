package ru.otus.hw;

import ru.otus.hw.migration.H2Migration;
import ru.otus.hw.model.Customer;
import ru.otus.hw.model.Item;
import ru.otus.hw.repository.CustomerRepository;
import ru.otus.hw.repository.CustomerRepositoryImpl;
import ru.otus.hw.repository.ItemRepository;
import ru.otus.hw.repository.ItemRepositoryImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        H2Migration.databaseInit();

        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        List<Item> itemsByCustomerId = customerRepository.getItemsByCustomerId(1L);
        System.out.printf("Items for customer with ID=%d is: {%s}", 1L, itemsByCustomerId);

        ItemRepository itemRepository = new ItemRepositoryImpl();
        Customer customer = itemRepository.findCustomersByItemId(4L);
        System.out.printf("Customer for item with ID=%d is: {%s}", 4L, customer);

        System.out.printf("Prepare for deleting item {%s}", itemsByCustomerId.get(0));
        itemRepository.remove(itemsByCustomerId.get(0));

        System.out.printf("Prepare for deleting customer {%s}", customer);
        customerRepository.remove(customer);
    }
}