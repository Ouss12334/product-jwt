package com.trial.product.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import com.trial.product.model.Product;
import com.trial.product.model.Shopping;
import com.trial.product.repository.ShoppingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * using generics for the common behavior in wishlist and cart
 */
@Slf4j
@RequiredArgsConstructor
public class ShoppingService<T extends Shopping, E extends ShoppingRepository<T>> {

  private final E shoppingRepository;

  public void addToShopping(Class<T> clazz, String username, Product product) {
    // get existing
    var shop = shoppingRepository.findByUsername(username);
    // if exists add the product
    if (shop != null)
      shop.getProducts().add(product);
    else {
      try {
        shop = clazz.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
          | NoSuchMethodException | SecurityException e) {
        log.info("error creating instance of {}", clazz);
        e.printStackTrace();
      }
      shop.setUsername(username);
      shop.setProducts(Collections.singletonList(product));
    }
    // add or update wishlist
    shoppingRepository.save(shop);
  }

  public T getShoppingList(Class<T> clazz, String username) {
    var shop = shoppingRepository.findByUsername(username);
    if (shop == null)
      try {
        return shop = clazz.getDeclaredConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException | NoSuchMethodException | SecurityException e) {
        log.info("error creating instance of {}", clazz);
        e.printStackTrace();
      }
    return shop;
  }

}
