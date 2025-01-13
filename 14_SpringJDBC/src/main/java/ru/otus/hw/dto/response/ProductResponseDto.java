package ru.otus.hw.dto.response;

import ru.otus.hw.model.Product;
import lombok.*;

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
                .name(product.getTitle())
                .price(product.getPrice().doubleValue())
                .build();
    }
}
