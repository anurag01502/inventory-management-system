package com.inventory.management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    
    @Column(name = "product_name",unique = true)
    private String productName;
    
    @Column(name = "product_price")
    private BigDecimal  productPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToOne(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private InventoryModel inventory;

    
    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    
    private List<OrderItemsModel> orderItems;
    public List<OrderItemsModel> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemsModel> orderItems) {
		this.orderItems = orderItems;
		
	
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal  getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal  productPrice) {
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public InventoryModel getInventory() {
		return inventory;
	}

	public void setInventory(InventoryModel inventory) {
	    this.inventory = inventory;

	    if (inventory != null) {
	        inventory.setProduct(this);
	    }
	}

    // getters and setters
	
	
}