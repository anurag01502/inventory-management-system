package com.inventory.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.management.model.OrderItemsModel;

public interface OrderItemRepository extends JpaRepository<OrderItemsModel,Long> {

}
