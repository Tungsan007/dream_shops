package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.requests.AddProductRequest;
import com.dailycodework.dream_shops.requests.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    List<Product> getProductsByName(String name);

    void deleteProductById(Long id);
    Product updateProduct(Long productId, UpdateProductRequest product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(Long categoryId);
    List<Product> getProductsByCategoryName(String categoryName);
    List<Product> getProductsByBrandName(String brandName);
    List<Product> getProductsByCategoryAndBrandName(String categoryName, String brandName);
    List<Product> getProductsByBrandAndName(String productName, String brandName);
    List<Product> countProductsByBrandAndName(String brandName, String productName);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
