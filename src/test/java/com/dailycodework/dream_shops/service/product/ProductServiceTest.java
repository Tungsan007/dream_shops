//package com.dailycodework.dream_shops.service.product;
//
//import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
//import com.dailycodework.dream_shops.model.Product;
//import com.dailycodework.dream_shops.repository.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    private Product mockProduct;
//
//    @BeforeEach
//    void setUp() {
//        mockProduct = new Product();
//        mockProduct.setId(1L);
//        mockProduct.setName("Test Product");
//        mockProduct.setBrand("Test Brand");
//    }
//
//    @Test
//    void getProductById_WhenProductExists_ShouldReturnProduct() {
//        // Arrange
//        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
//
//        // Act
//        Product result = productService.getProductById(1L);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Test Product", result.getName());
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getProductById_WhenProductNotFound_ShouldThrowException() {
//        // Arrange
//        when(productRepository.findById(99L)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ProductNotFoundException.class, () -> {
//            productService.getProductById(99L);
//        });
//        verify(productRepository, times(1)).findById(99L);
//    }
//}
package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product fakeProduct;

    @BeforeEach
    void setUp() {
        fakeProduct = new Product();
        fakeProduct.setId(1L);
        fakeProduct.setName("Iphone 17");
        fakeProduct.setBrand("Apple");
    }

    @Test
    void getProductIdById_whenProductExist_shouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(fakeProduct));

        Product result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Iphone 17", result.getName());
        assertEquals("Apple", result.getBrand());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_whenProductNotExits_shouldReturnEmptyOptional() {
        //Arrange
        when(productRepository.findById(99L)).thenReturn(Optional.empty());


        //Asserts
        assertThrows(ProductNotFoundException.class, () -> {
           productService.getProductById(99L);
       });
        verify(productRepository, times(1)).findById(99L);

    }

}
