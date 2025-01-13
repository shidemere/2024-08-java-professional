package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.request.ProductRequestDto;
import ru.otus.hw.dto.response.ProductResponseDto;
import ru.otus.hw.exception.EntityNotFoundException;
import ru.otus.hw.model.Product;
import ru.otus.hw.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto create(ProductRequestDto product) {
        Product entity = ProductRequestDto.toEntity(product);
        entity.setNew(true);
        Product createdProduct = productRepository.save(entity);
        return ProductResponseDto.toDto(createdProduct);
    }

    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> productsList = productRepository.findAll();
        return productsList.stream()
                .map(ProductResponseDto::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto findById(int id) {
        Product product = productRepository.findById((long) id).orElseThrow(() -> new EntityNotFoundException("Not found entity with id" + id));
        return ProductResponseDto.toDto(product);
    }

    @Override
    public ProductResponseDto update(ProductRequestDto product) {
        Product entity = ProductRequestDto.toEntity(product);
        entity.setNew(false);
        Product updatedProduct = productRepository.save(entity);
        return ProductResponseDto.toDto(updatedProduct);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
