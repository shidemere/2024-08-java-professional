package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.model.Product;
import ru.otus.hw.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

}
