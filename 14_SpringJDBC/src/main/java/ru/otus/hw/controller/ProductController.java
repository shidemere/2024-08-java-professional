package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.request.ProductRequestDto;
import ru.otus.hw.dto.response.ProductResponseDto;
import ru.otus.hw.model.Product;
import ru.otus.hw.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto product) {
        Product createdProduct = productService.create(product);
        return ProductResponseDto.toDto(createdProduct);
    }

    @PutMapping("/product")
    public ProductResponseDto updateProduct(@RequestBody ProductRequestDto product) {
        Product updatedProduct = productService.update(product);
        return ProductResponseDto.toDto(updatedProduct);
    }

    @GetMapping("/product/{id}")
    public ProductResponseDto getProduct(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return ProductResponseDto.toDto(product);
    }

    @GetMapping("/product")
    public List<ProductResponseDto> getProducts() {
        List<Product> productsList = productService.findAll();
        return productsList.stream()
                .map(ProductResponseDto::toDto)
                .toList();
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }
}
