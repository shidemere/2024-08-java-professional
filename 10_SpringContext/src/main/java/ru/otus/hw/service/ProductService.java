package ru.otus.hw.service;

import ru.otus.hw.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(int id);
}
