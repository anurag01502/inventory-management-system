package com.inventory.management.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.inventory.management.dto.ProductDto;
import com.inventory.management.exception.CustomRuntimeException;
import com.inventory.management.model.ProductModel;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.rowmapper.ProductRowMapper;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // CREATE
    public ProductModel saveProduct(ProductModel productModel) {

        List<ProductModel> product =
                productRepository.findByProductNameContainingIgnoreCase(productModel.getProductName());

        if (!product.isEmpty()) {
            throw new CustomRuntimeException(
                    "Product already exists with this name!",
                    HttpStatus.BAD_REQUEST);
        }

        return productRepository.save(productModel);
    }

    // DELETE
    public void deleteProductById(long productId) {

        if (!productRepository.existsById(productId)) {
            throw new CustomRuntimeException(
                    "Product not found!",
                    HttpStatus.NOT_FOUND);
        }

        productRepository.deleteById(productId);
    }

    // UPDATE
    public ProductModel updateProduct(long productId, ProductModel productModel) {

        ProductModel existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new CustomRuntimeException(
                                "Product not found!",
                                HttpStatus.NOT_FOUND));

        existingProduct.setProductName(productModel.getProductName());
        existingProduct.setProductPrice(productModel.getProductPrice());
        existingProduct.setDescription(productModel.getDescription());
        existingProduct.setCategoryType(productModel.getCategoryType());

        
        existingProduct.getInventory().setAvailableStock(productModel.getInventory().getAvailableStock());

        
        return productRepository.save(existingProduct);
    }

    // GET BY ID
    public ProductDto getProductById(long productId) {

        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new CustomRuntimeException(
                                "Product not found!",
                                HttpStatus.NOT_FOUND));

        return ProductRowMapper.convertToDTO(product);
    }

    // GET ALL
    public Page<ProductDto> getAllProducts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable)
                .map(ProductRowMapper::convertToDTO);
    }

    // FIND BY NAME
    public List<ProductDto> findByProductName(String productName) {

    	List< ProductModel> product = productRepository.findByProductNameContainingIgnoreCase(productName);

        List<ProductDto> productsDto= product.stream().map(ProductRowMapper::convertToDTO).collect(Collectors.toList());
        
        
        return productsDto;
    }
}