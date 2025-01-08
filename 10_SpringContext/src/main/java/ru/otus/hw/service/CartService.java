package ru.otus.hw.service;

import ru.otus.hw.model.Cart;
import ru.otus.hw.model.Product;

import java.util.List;

public interface CartService {
    Cart addProductToCart(Product product, Cart cart);

    Cart removeProductFromCart(Product product, Cart cart);

    Cart addProductsToCart(List<Product> list, Cart cart);

    Cart removeProductsFromCart(List<Product> list, Cart cart);
}
