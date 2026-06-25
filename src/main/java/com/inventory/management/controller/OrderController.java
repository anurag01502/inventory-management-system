package com.inventory.management.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.inventory.management.dto.CreateOrder;
import com.inventory.management.dto.ProfileResponseDto;
import com.inventory.management.model.OrderModel;
import com.inventory.management.service.OrderService;
import com.inventory.management.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
		this.userService = userService;
    }

    // Place Order
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/place")
    public ResponseEntity<OrderModel> placeOrder(
            @RequestBody CreateOrder createOrder,Authentication authentication) {

    	ProfileResponseDto findUser = userService.findByIdentifier(authentication.getName()) ;
    	
    	createOrder.setUserId(findUser.getUserId());
    	
        OrderModel order = orderService.placeOrder(createOrder);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    // View All Orders (Pagination)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderModel>> viewAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<OrderModel> orders = orderService.viewAllOrders(page, size);

        return ResponseEntity.ok(orders);
    }


    // View Order By Order Id
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderModel> viewByOrderId(
            @PathVariable long orderId) {

        OrderModel order = orderService.viewByOrderId(orderId);

        return ResponseEntity.ok(order);
    }


    // View All Orders By User Id
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderModel>> viewAllOrdersByUserId(
            @PathVariable long userId) {

        List<OrderModel> orders = orderService.viewAllOrdersByUserId(userId);

        return ResponseEntity.ok(orders);
    }





    // Update Order Status
    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderModel> updateOrderStatus(
            @PathVariable long orderId,
            @RequestParam String status) {

        OrderModel updatedOrder =
                orderService.updateOrderStatus(orderId, status);

        return ResponseEntity.ok(updatedOrder);
    }


    // Cancel Order
    @PatchMapping("/cancel/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderModel> cancelOrder(
            @PathVariable long orderId) {

        OrderModel cancelledOrder =
                orderService.cancelOrder(orderId);

        return ResponseEntity.ok(cancelledOrder);
    }

}