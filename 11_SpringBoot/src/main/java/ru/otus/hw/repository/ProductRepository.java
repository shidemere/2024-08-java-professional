package ru.otus.hw.repository;

import org.springframework.stereotype.Repository;
import ru.otus.hw.exception.EntityNotFoundException;
import ru.otus.hw.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepository {

    private final List<Product> productList = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public List<Product> findAll() {
        return productList;
    }

    public Product create(Product product) {
        product.setId(idCounter.getAndIncrement());
        productList.add(product);
        return product;
    }

    public Product update(Product product) {
        int index = (int) product.getId();
        Product findedProduct = getProductById(index);
        findedProduct.setName(product.getName());
        findedProduct.setPrice(product.getPrice());
        return findedProduct;
    }

    public void delete(int id) {
        productList.remove(id);
    }

    public Product getProductById(int id) {
        return Optional.ofNullable(productList.get(id)).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " not found")
        );
    }
}
