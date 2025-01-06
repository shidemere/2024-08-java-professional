package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.model.Product;
import ru.otus.hw.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id);
    }

}
