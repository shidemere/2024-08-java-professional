package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.model.Cart;
import ru.otus.hw.model.Product;
import ru.otus.hw.service.CartServiceImpl;
import ru.otus.hw.service.ProductServiceImpl;

import java.util.List;

@ComponentScan
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        ProductServiceImpl productServiceImpl = context.getBean(ProductServiceImpl.class);
        CartServiceImpl cartServiceImpl = context.getBean(CartServiceImpl.class);
        Cart cart = context.getBean(Cart.class);
        Product product = productServiceImpl.findById(4);
        System.out.printf("Founded product by id: %s%n", product);

        System.out.printf("Added product to the cart: %s%n", cartServiceImpl.addProductToCart(product, cart));
        System.out.printf("Removed product from the cart: %s%n", cartServiceImpl.removeProductFromCart(product, cart));

        Cart newCart = context.getBean(Cart.class);

        List<Product> allProducts = productServiceImpl.findAll();
        System.out.printf("Get all products: %s%n", allProducts);

        System.out.printf("Add all products to the cart: %s%n", cartServiceImpl.addProductsToCart(allProducts, newCart));
        System.out.printf("Removed all products from the cart: %s%n", cartServiceImpl.removeProductsFromCart(allProducts, newCart));

        context.close();
    }
}