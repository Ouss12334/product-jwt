package com.trial.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trial.product.exceptions.ProductNotFoundException;
import com.trial.product.model.Cart;
import com.trial.product.model.Product;
import com.trial.product.model.Wishlist;
import com.trial.product.repository.CartRepository;
import com.trial.product.repository.ProductRepository;
import com.trial.product.repository.WishlistRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ShoppingService<Wishlist, WishlistRepository> wishlistService;
    private ShoppingService<Cart, CartRepository> cartService;
    
    public ProductServiceImpl(ProductRepository repository, WishlistRepository wishlistRepository,
        CartRepository cartRepository) {
      this.repository = repository;
      this.wishlistService = new ShoppingService<Wishlist,WishlistRepository>(wishlistRepository);
      this.cartService = new ShoppingService<Cart,CartRepository>(cartRepository);
    }

    @Override
    public List<Product> getProducts() {
        log.info("fetching products");
        return repository.findAll();
    }

    @Override
    public Product upsertProduct(Product p) {
        log.info("creating new product");
        return repository.save(p);
    }

    @Override
    public Product getProduct(int id) throws ProductNotFoundException {
        log.info("fetching product {}", id);
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }

    @Override
    public Product updateProduct(int id, Product p) throws ProductNotFoundException {
        log.info("updating product {}", id);
        if (repository.existsById(id)) {
            // if id not set
            if (p.getId() == 0) p.setId(id);
            return repository.save(p);
        }
        throw new ProductNotFoundException();
    }

    @Override
    public void deleteProduct(int id) throws ProductNotFoundException {
        log.info("deleting product");
        if (repository.existsById(id)) {
            repository.deleteById(id); 
            return;
        }
        throw new ProductNotFoundException();
    }

    /////////////////////////
    /// wishlist and cart
    public void addToWishlist(String username, int productId) throws ProductNotFoundException {
        log.info("adding product to wishlist");
        wishlistService.addToShopping(Wishlist.class, username, this.getProduct(productId));
    }
    
    @Override
    public void addToCart(String username, int productId) throws ProductNotFoundException {
        log.info("adding product to cart");
        cartService.addToShopping(Cart.class, username, this.getProduct(productId));
    }

    public Wishlist getWishlist(String username) {
        log.info("retrieving wishlist for {}", username);
        return wishlistService.getShoppingList(Wishlist.class, username);
    }

    public Cart getCart(String username) {
        log.info("retrieving cart for {}", username);
        return cartService.getShoppingList(Cart.class, username);
    }

}
