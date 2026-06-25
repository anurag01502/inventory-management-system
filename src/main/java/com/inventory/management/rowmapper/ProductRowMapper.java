package com.inventory.management.rowmapper;

import com.inventory.management.dto.ProductDto;
import com.inventory.management.model.ProductModel;

public class ProductRowMapper {
	
	
	public static ProductDto convertToDTO(ProductModel product) {
		ProductDto dto = new ProductDto();

	    dto.setProductId(product.getProductId());
	    dto.setProductName(product.getProductName());
	    dto.setProductPrice(product.getProductPrice());
	    dto.setDescription(product.getDescription());
	    dto.setCategoryType(product.getCategoryType());

	    return dto;
	}
	
	

}
