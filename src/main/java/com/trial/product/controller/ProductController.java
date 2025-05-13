package com.trial.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trial.product.exceptions.ProductNotFoundException;
import com.trial.product.model.Cart;
import com.trial.product.model.Product;
import com.trial.product.model.Wishlist;
import com.trial.product.service.ProductService;

import lombok.AllArgsConstructor;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    /**
     * get all products
     */
    @GetMapping
    public List<Product> getProducts() {
        return service.getProducts();
    }

    /**
     * create or update a product
     */
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')") // 'scope_' and 'role_' added by default when reading jwt token
    public void createProduct(@RequestBody Product product) {
        service.upsertProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) throws ProductNotFoundException {
        return service.getProduct(id);
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) throws ProductNotFoundException {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void deleteProduct(@PathVariable int id) throws ProductNotFoundException {
        service.deleteProduct(id);
    }

    /////////////////////////
    /// wishlist and cart
    @PostMapping("/wishlist/{id}")
    public void addToWishlist(Authentication auth, @PathVariable("id") int productId) throws ProductNotFoundException {
        service.addToWishlist(auth.getName(), productId);
    }

    @PostMapping("/cart/{id}")
    public void addToCart(Authentication auth, @PathVariable("id") int productId) throws ProductNotFoundException {
        service.addToCart(auth.getName(), productId);
    }

    @GetMapping("/wishlist")
    public Wishlist getUserWishlist(Authentication auth) {
        return service.getWishlist(auth.getName());
    }

    @GetMapping("/cart")
    public Cart getUserCart(Authentication auth) {
        return service.getCart(auth.getName());
    }
    
}
