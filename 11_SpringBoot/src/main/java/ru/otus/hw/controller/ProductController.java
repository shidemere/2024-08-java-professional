package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.request.ProductCreateRequestDto;
import ru.otus.hw.dto.request.ProductUpdateRequestDto;
import ru.otus.hw.dto.response.ProductResponseDto;
import ru.otus.hw.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ProductResponseDto createProduct(@RequestBody ProductCreateRequestDto product) {
        return productService.create(product);
    }

    @PutMapping("/product")
    public ProductResponseDto updateProduct(@RequestBody ProductUpdateRequestDto product) {
        return productService.update(product);
    }

    @GetMapping("/product/{id}")
    public ProductResponseDto getProduct(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @GetMapping("/product")
    public List<ProductResponseDto> getProducts() {
        return productService.findAll();
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }
}
