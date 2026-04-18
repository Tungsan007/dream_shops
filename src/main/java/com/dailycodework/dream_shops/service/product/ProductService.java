package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public void updateProduct(Long productId, Product product) {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandName(String brandName) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrandName(String categoryName, String brandName) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String productName, String brandName) {
        return List.of();
    }

    @Override
    public List<Product> countProductsByBrandAndName(String brandName, String productName) {
        return List.of();
    }
}
