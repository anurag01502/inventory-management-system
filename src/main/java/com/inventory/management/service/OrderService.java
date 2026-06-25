package com.inventory.management.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inventory.management.dto.CreateOrder;
import com.inventory.management.dto.CreateOrderItems;
import com.inventory.management.exception.CustomRuntimeException;
import com.inventory.management.model.OrderItemsModel;
import com.inventory.management.model.OrderModel;
import com.inventory.management.model.ProductModel;
import com.inventory.management.repository.OrderRepository;
import com.inventory.management.repository.ProductRepository;

@Service
public class OrderService {


	private final OrderRepository orderRepository;
	
	private final ProductRepository productRepository;
	
	public OrderService(OrderRepository orderRepository,ProductRepository productRepository)
	{
		this.orderRepository=orderRepository;
		this.productRepository=productRepository;
	}
	
    public OrderModel placeOrder(CreateOrder createOrder) {

        OrderModel order = new OrderModel();

        order.setUserId(createOrder.getUserId());
        order.setOrderStatus("PLACED");

        List<OrderItemsModel> orderItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderItems itemDto : createOrder.getOrderItems()) {

            ProductModel product = productRepository
                    .findById(itemDto.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            OrderItemsModel orderItem = new OrderItemsModel();

            orderItem.setOrder(order); // important

            orderItem.setProduct(product);

            orderItem.setQuantity(itemDto.getQuantity());

            orderItem.setUnitPrice(product.getProductPrice());

            BigDecimal itemTotal =
                    product.getProductPrice()
                           .multiply(BigDecimal.valueOf(itemDto.getQuantity()));

            orderItem.setTotalPrice(itemTotal);

            totalAmount = totalAmount.add(itemTotal);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
	
	public Page<OrderModel> viewAllOrders(int page, int size)
	{
		 Pageable pageable = PageRequest.of(page, size);
		return orderRepository.findAll(pageable);
	}
	
	public OrderModel viewByOrderId(long orderId)
	{
		OrderModel order = orderRepository.findById(orderId).orElseThrow(()-> new CustomRuntimeException("Order not Found!", HttpStatus.NOT_FOUND));	
		return order;
	}
	
	
	public List<OrderModel> viewAllOrdersByUserId(long userId)
	{
		
		List<OrderModel>  orders= orderRepository.findByUserId(userId);
		return orders;
	}
	
	public OrderModel viewSpecificOrderByUserId(long userId,long orderId)
	{
		
		OrderModel  viewOrder = orderRepository.findByUserIdAndOrderId(userId, orderId).orElseThrow(()-> new CustomRuntimeException("Order not Found!", HttpStatus.NOT_FOUND));	
		return viewOrder;
	}	
	
	public OrderModel updateOrderStatus(long orderId, String status)
	{
		
		OrderModel orderModel = viewByOrderId(orderId);	
		orderModel.setOrderStatus(status);
		return orderRepository.save(orderModel);
	}
	
	
	public OrderModel cancelOrder(long orderId)
	{
		OrderModel orderModel = viewByOrderId(orderId);		
		orderModel.setOrderStatus("Cancel");
		return orderRepository.save(orderRepository.save(orderModel));

	}
	
}
