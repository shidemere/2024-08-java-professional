package ru.otus.hw.repository;

import org.springframework.stereotype.Repository;
import ru.otus.hw.exception.EntityNotFoundException;
import ru.otus.hw.model.Product;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    List<Product> products;

    public ProductRepository() {
        init();
    }


    private void init() {
        products = List.of(
                new Product(1, "Расчёска", 10.0),
                new Product(2, "Батарейки", 20.0),
                new Product(3, "Молоко", 30.0),
                new Product(4, "Оптимус Прайм", 40.0),
                new Product(5, "Звезда смерти", 50.0),
                new Product(6, "Перо жар-птицы", 60.0),
                new Product(7, "Хлыст Индианы", 70.0),
                new Product(8, "Фигурка Конана Варвара", 80.0),
                new Product(9, "Футболка \"Без баб\"", 90.0),
                new Product(10, "Котопес", 100.0)
        );
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    public Product findById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No product found with id " + id));
    }
}
