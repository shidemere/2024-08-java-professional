package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.model.Cart;
import ru.otus.hw.model.Product;

import java.util.List;

@Service
public class CartService {

    public Cart addProductToCart(Product product, Cart cart) {
        cart.getProducts().add(product);
        return cart;
    }

    public Cart removeProductFromCart(Product product, Cart cart) {
        cart.getProducts().remove(product);
        return cart;
    }

    public Cart addProductsToCart(List<Product> list, Cart cart) {
        cart.getProducts().addAll(list);
        return cart;
    }

    public Cart removeProductsFromCart(List<Product> list, Cart cart) {
        List<Product> products = cart.getProducts();
        products.removeAll(list);
        return cart;
    }


}
