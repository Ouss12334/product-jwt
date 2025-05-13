package com.trial.product.service;

import java.util.List;

import com.trial.product.exceptions.ProductNotFoundException;
import com.trial.product.model.Cart;
import com.trial.product.model.Product;
import com.trial.product.model.Wishlist;

public interface ProductService {

    List<Product> getProducts();

    Product upsertProduct(Product p);
    
    Product getProduct(int id) throws ProductNotFoundException;

    Product updateProduct(int id, Product p) throws ProductNotFoundException;

    void deleteProduct(int id) throws ProductNotFoundException;

    /////////////////////////
    /// wishlist and cart
    void addToWishlist(String username, int productId) throws ProductNotFoundException;

    void addToCart(String username, int productId) throws ProductNotFoundException;

    Wishlist getWishlist(String username);

    Cart getCart(String username);
}