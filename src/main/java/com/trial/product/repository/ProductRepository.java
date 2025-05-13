package com.trial.product.repository;

import org.springframework.stereotype.Repository;

import com.trial.product.model.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findAll();
}
