package ru.otus.hw.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.model.Product;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
