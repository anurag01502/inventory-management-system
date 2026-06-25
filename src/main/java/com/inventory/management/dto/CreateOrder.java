package com.inventory.management.dto;

import java.util.List;

public class CreateOrder {

	
	
	Long userId;
	
	
	List<CreateOrderItems> orderItems;


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public List<CreateOrderItems> getOrderItems() {
		return orderItems;
	}


	public void setOrderItems(List<CreateOrderItems> orderItems) {
		this.orderItems = orderItems;
	}
	
	
	
}
