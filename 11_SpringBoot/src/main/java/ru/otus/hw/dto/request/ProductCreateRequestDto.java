package ru.otus.hw.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.model.Product;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {

    private String name;
    private double price;

    public static Product toEntity(ProductCreateRequestDto productCreateRequestDto) {
        Product product = new Product();
        product.setName(productCreateRequestDto.getName());
        product.setPrice(productCreateRequestDto.getPrice());
        return product;
    }

}
