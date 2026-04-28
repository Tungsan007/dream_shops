package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CategoryRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.requests.AddProductRequest;
import com.dailycodework.dream_shops.requests.UpdateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override

    public Product addProduct(AddProductRequest request) {
        log.info(">>> Request nhận được: {}", request);
        log.info(">>> Category object: {}", request.getCategory());
        log.info(">>> Category name: {}", request.getCategory().getName());
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    log.info(">>> Không tìm thấy → Tạo category mới");
                    Category newCategory = new Category(request.getCategory().getName());
                    log.info(">>> Category trước khi save: {}", newCategory);
                    Category saved = categoryRepository.save(newCategory);
                    log.info(">>> Category sau khi save: {}", saved);
                    return saved;
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    public Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public Product updateProduct(Long productId, UpdateProductRequest request) {
       return productRepository.findById(productId)
               .map(existingProduct -> updateExistingProduct(existingProduct, request))
               .map(productRepository::save)
               .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    }

    public Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);

    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }


    @Override
    public List<Product> getProductsByBrandName(String brandName) {
        return productRepository.findByBrand(brandName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrandName(String categoryName, String brandName) {
        return productRepository.findByCategoryNameAndBrand(categoryName, brandName);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String productName, String brandName) {
        return productRepository.findByBrandAndName(productName, brandName);
    }

    @Override
    public List<Product> countProductsByBrandAndName(String brandName, String productName) {
        return productRepository.countByBrandAndName(brandName, productName);
    }
}
