package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brandName);

    List<Product> findByBrandAndName(String productName, String brandName);

    List<Product> countByBrandAndName(String brandName, String productName);
}
