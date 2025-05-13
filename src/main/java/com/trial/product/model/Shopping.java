package com.trial.product.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * not an entity but the base for wishlist and cart
 */
@Data
@MappedSuperclass
public class Shopping {
  @Id
  private String username; // basket/wishlist for each user based on unique username
  @OneToMany(cascade = CascadeType.ALL)
  private List<Product> products;

}
