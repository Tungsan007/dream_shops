package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest   // ← khởi động Spring Boot thật, kết nối H2
@Transactional    // ← sau mỗi test tự rollback, DB luôn sạch
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;  // bean thật

    @Autowired
    private ProductRepository productRepository;  // repo thật

    private Product savedProduct;

    @BeforeEach
    void setUp() {
        // Insert 1 product thật vào H2 trước mỗi test
        Product product = new Product();
        product.setName("Integration Product");
        product.setBrand("Brand X");
        product.setPrice(new BigDecimal("99.99"));
        product.setInventory(10);
        product.setDescription("Test description");

        savedProduct = productRepository.save(product);
    }

    @Test
    void getProductById_WhenExists_ShouldReturnCorrectProduct() {
        // Gọi service thật → repo thật → H2
        Product result = productService.getProductById(savedProduct.getId());

        assertNotNull(result);
        assertEquals("Integration Product", result.getName());
        assertEquals("Brand X", result.getBrand());
        assertEquals(new BigDecimal("99.99"), result.getPrice());
    }

    @Test
    void getProductById_WhenNotExists_ShouldThrowException() {
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(999999L);
        });
    }
}