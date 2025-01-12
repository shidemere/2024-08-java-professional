package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.request.ProductCreateRequestDto;
import ru.otus.hw.dto.request.ProductUpdateRequestDto;
import ru.otus.hw.dto.response.ProductResponseDto;
import ru.otus.hw.model.Product;
import ru.otus.hw.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto create(ProductCreateRequestDto product) {
        Product entity = ProductCreateRequestDto.toEntity(product);
        Product createdProduct  = productRepository.create(entity);
        return ProductResponseDto.toDto(createdProduct );
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
        Product product = productRepository.getProductById(id);
        return ProductResponseDto.toDto(product);
    }

    @Override
    public ProductResponseDto update(ProductUpdateRequestDto product) {
        Product entity = ProductUpdateRequestDto.toEntity(product);
        Product updatedProduct = productRepository.update(entity);
        return ProductResponseDto.toDto(updatedProduct);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }
}
