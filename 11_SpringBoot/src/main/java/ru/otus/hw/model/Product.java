package ru.otus.hw.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private long id;
    private String name;
    private BigDecimal price;
}
