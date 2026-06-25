package com.inventory.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.management.model.InventoryModel;

public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {

}
