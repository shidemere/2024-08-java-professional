package ru.otus.hw.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table("PRODUCTS")
public class Product implements Persistable<Long> {
    @Id
    private Long id;
    private String title;
    private BigDecimal price;
    @Transient
    @Setter
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PersistenceCreator
    public Product(long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}
