package com.inventory.management.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {

    private Long productId;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100,
          message = "Product name must contain 2 to 100 characters")
    private String productName;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01",
                message = "Product price must be greater than 0")
    @Digits(integer = 10, fraction = 2,
            message = "Price can have at most 2 decimal places")
    private BigDecimal productPrice;

    @Size(max = 500,
          message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Category type cannot be blank")
    @Size(max = 50,
          message = "Category type cannot exceed 50 characters")
    private String categoryType;

    public ProductDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}