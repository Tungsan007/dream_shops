package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.requests.AddProductRequest;
import com.dailycodework.dream_shops.requests.UpdateProductRequest;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> productsDto = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found", productsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", e.getMessage()));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
        try {
            Product product = productService.addProduct(request);
            ProductDto productDto = productService.convertToDto(product);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse("Added successfully", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed!", e.getMessage()));
        }
    }
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        try {
            Product product = productService.updateProduct(productId, request);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Update success", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed", e.getMessage()));
        }
    }
    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Success!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Failed!", e.getMessage()));
        }
    }
    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brand, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(productName, brand);
            List<ProductDto> productsDto = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/products/{productName}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String productName) {
        try {
            List<Product> products = productService.getProductsByName(productName);
            List<ProductDto> productsDto = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsByBrandName(brandName);
            List<ProductDto> productsDto = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@RequestParam String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategoryName(categoryName);
            List<ProductDto> productsDto = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No category found", NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productsDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/products/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(name, brand);
            return ResponseEntity.ok(new ApiResponse("Found!", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

}
