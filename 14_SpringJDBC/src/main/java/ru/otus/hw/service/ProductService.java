package ru.otus.hw.service;

import ru.otus.hw.dto.request.ProductRequestDto;
import ru.otus.hw.model.Product;

import java.util.List;

public interface ProductService {

    Product create(ProductRequestDto product);
    List<Product> findAll();
    Product findById(int id);
    Product update(ProductRequestDto product);
    void delete(long id);
}
