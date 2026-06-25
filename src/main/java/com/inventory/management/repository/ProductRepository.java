package com.inventory.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.management.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel,Long>{

	
	List<ProductModel> findByProductNameContainingIgnoreCase(String productName);    
    List<ProductModel> findByCategoryTypeIgnoreCase(String categoryType);
    
    
}
