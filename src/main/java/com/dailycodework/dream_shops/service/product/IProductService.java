package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product getProductById(Long id);

    void deleteProductById(Long id);
    void updateProduct(Long productId, Product product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(Long categoryId);
    List<Product> getProductsByCategoryName(String categoryName);
    List<Product> getProductsByBrandName(String brandName);
    List<Product> getProductsByCategoryAndBrandName(String categoryName, String brandName);
    List<Product> getProductsByBrandAndName(String productName, String brandName);
    List<Product> countProductsByBrandAndName(String brandName, String productName);
}
