package com.trial.product.repository;

import com.trial.product.model.Shopping;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * wish or basket repository, as they're similar but both based on username
 */
// @Repository
@NoRepositoryBean
public interface ShoppingRepository<T extends Shopping> extends CrudRepository<T, String> {

    T findByUsername(String username);
}
