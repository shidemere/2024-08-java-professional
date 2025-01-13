package ru.otus.hw.service;

import ru.otus.hw.dto.request.ProductRequestDto;
import ru.otus.hw.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto create(ProductRequestDto product);
    List<ProductResponseDto> findAll();
    ProductResponseDto findById(int id);
    ProductResponseDto update(ProductRequestDto product);
    void delete(long id);
}
