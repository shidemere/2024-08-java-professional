package ru.otus.hw.service;

import ru.otus.hw.dto.request.ProductCreateRequestDto;
import ru.otus.hw.dto.request.ProductUpdateRequestDto;
import ru.otus.hw.dto.response.ProductResponseDto;
import ru.otus.hw.model.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    ProductResponseDto create(ProductCreateRequestDto product);
    List<ProductResponseDto> findAll();
    ProductResponseDto findById(int id);
    ProductResponseDto update(ProductUpdateRequestDto product);
    void delete(int id);
}
