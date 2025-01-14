package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.request.ProductRequestDto;
import ru.otus.hw.exception.EntityNotFoundException;
import ru.otus.hw.model.Product;
import ru.otus.hw.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(ProductRequestDto product) {
        Product entity = ProductRequestDto.toEntity(product);
        entity.setNew(true);
        return productRepository.save(entity);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById((long) id).orElseThrow(() -> new EntityNotFoundException("Not found entity with id" + id));
    }

    @Override
    public Product update(ProductRequestDto product) {
        Product entity = ProductRequestDto.toEntity(product);
        entity.setNew(false);
        return productRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
