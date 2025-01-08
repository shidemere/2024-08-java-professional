package ru.otus.hw.dto.response;

import lombok.*;
import ru.otus.hw.model.Product;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private long id;
    private String name;
    private double price;

    public static ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
