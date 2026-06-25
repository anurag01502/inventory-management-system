package com.inventory.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.management.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel,Long> {

	
	
	
	List<OrderModel> findByUserId(long userId);
	
	Optional<OrderModel> findByUserIdAndOrderId(long userId,long orderId);
}
