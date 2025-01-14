package ru.otus.hw.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.model.Product;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private Long id;
    private String name;
    private double price;

    public static Product toEntity(ProductRequestDto productRequestDto) {
        Product product = new Product();
        if (productRequestDto.id != null) {
            product.setId(productRequestDto.getId());
        }
        product.setTitle(productRequestDto.getName());
        product.setPrice(BigDecimal.valueOf(productRequestDto.getPrice()));
        return product;
    }

}
