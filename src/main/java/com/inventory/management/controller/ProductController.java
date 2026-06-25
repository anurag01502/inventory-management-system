package com.inventory.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inventory.management.dto.ProductDto;
import com.inventory.management.model.ProductModel;
import com.inventory.management.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(
            @Valid @RequestBody ProductModel productModel) {

        ProductModel savedProduct = productService.saveProduct(productModel);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // ADMIN and USER
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable long productId) {

        ProductDto productDto = productService.getProductById(productId);

        return ResponseEntity.ok(productDto);
    }

    // ADMIN and USER
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ProductDto> products =
                productService.getAllProducts(page, size);

        return ResponseEntity.ok(products);
    }

    // ADMIN and USER
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/name/{productName}")
    public ResponseEntity<List<ProductDto>> getProductByName(
            @PathVariable String productName) {

        return ResponseEntity.ok( productService.findByProductName(productName));

    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductModel> updateProduct(
            @PathVariable long productId,
            @Valid @RequestBody ProductModel productModel) {

        ProductModel updatedProduct =
                productService.updateProduct(productId, productModel);

        return ResponseEntity.ok(updatedProduct);
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable long productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.noContent().build();
    }
}