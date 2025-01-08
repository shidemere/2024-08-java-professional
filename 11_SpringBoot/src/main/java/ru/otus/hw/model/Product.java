package ru.otus.hw.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private long id;
    private String name;
    private double price;
}
