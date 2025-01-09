package com.product.application.hive.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.application.hive.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
