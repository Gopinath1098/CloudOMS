package com.cloud.oms.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.oms.app.Status.OrderStatus;
import com.cloud.oms.app.dto.OrderDTO;
import com.cloud.oms.app.entity.OrderEntity;
import com.cloud.oms.app.exception.OrderNotCreatedException;
import com.cloud.oms.app.exception.OrderNotFoundException;
import com.cloud.oms.app.exception.OutofStockException;
import com.cloud.oms.app.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;

    private ProductService productService;

     public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO getOrderById(String id) {
        // Implement logic to retrieve order by ID
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return ConvertToDTO(orderEntity.orElseThrow(()->new OrderNotFoundException("Order not found with id: " + id)));
    }

    public List<OrderDTO> getOrderByState(OrderStatus state) {
        // Implement logic to retrieve order by state
        List<OrderEntity> orderEntities = (List<OrderEntity>) orderRepository.findByOrderState(state);
        if(orderEntities == null || orderEntities.isEmpty()) {
           throw new OrderNotFoundException("No Order Found with state: " + state);
        }
        return orderEntities.stream().map(this::ConvertToDTO).toList();
    }
    
    public String placeOrder(List<OrderDTO> orderDTO) {
        // Implement logic to place an order
        List<OrderEntity> savedOrders = null;
        for(OrderDTO order: orderDTO) {
            if(order.getQuantity() <= 0 || order.getQuantity() <= 0) {
                throw new OrderNotCreatedException("Invalid order details: Quantity and Total Price must be greater than zero");
            }
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setProduct(ProductService.ConvertToEntity(order.getProduct()));
            orderEntity.setQuantity(order.getQuantity());
            orderEntity.setTotalPrice(order.getTotalPrice());
            orderEntity.setOrderStatus(order.getOrderStatus());
            OrderEntity savedOrder = orderRepository.save(orderEntity);
            if (savedOrders == null) savedOrders = new ArrayList<>();
            savedOrders.add(savedOrder);
            updateProductStock(order.getProduct().getProductId(), order.getQuantity(), false);
        }

        if(savedOrders == null) {
            throw new OrderNotCreatedException("Failed to place order");
        }
        return savedOrders.size() + " Orders placed successfully";
    }

    public String updateOrder(String id) {
      OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("Order not found with id: " + id));
      if(orderEntity.getOrderStatus() == OrderStatus.NEW) {
        orderEntity.setOrderStatus(OrderStatus.CONFIRMED);
      }else if(productService.getProductById(orderEntity.getProduct().getProductId()).getProductStock() <= 0) {
        orderEntity.setOrderStatus(OrderStatus.CANCELLED);
        log.debug("Inventory is out of stock for order with id: " + id);
        throw new OutofStockException("Inventory is out of stock for order with id: " + id);
      }else if(orderEntity.getOrderStatus() == OrderStatus.CONFIRMED) {
        orderEntity.setOrderStatus(OrderStatus.PROCESSING);
      } else if(orderEntity.getOrderStatus() == OrderStatus.PROCESSING) {
        orderEntity.setOrderStatus(OrderStatus.SHIPPED); 
      } else if(orderEntity.getOrderStatus() == OrderStatus.SHIPPED) {
        orderEntity.setOrderStatus(OrderStatus.DELIVERED);
      } else {
        throw new OrderNotFoundException("Order with id: " + id + " cannot be updated from status: " + orderEntity.getOrderStatus());
      }
        OrderEntity orderentity = orderRepository.save(orderEntity);
        log.debug("Order updated successfully" + orderentity.getOrderStatus());
      return  orderentity.getOrderStatus() + " Order updated successfully";
    }

    public String returnOrder(List<OrderDTO> orderDTO) {

        for(OrderDTO order: orderDTO) {
            OrderEntity orderEntity = orderRepository.findById(order.getOrderId()).orElseThrow(()->new OrderNotFoundException("Order not found with id: " + order.getOrderId()));

        if(orderEntity.getOrderStatus() == OrderStatus.DELIVERED) {
            orderEntity.setOrderStatus(OrderStatus.RETURNED);
        } else {
            throw new OrderNotFoundException("Order with id: " + order.getOrderId() + " cannot be returned from status: " + orderEntity.getOrderStatus());
        }
        String productId = orderEntity.getProduct().getProductId();

        updateProductStock(productId, orderEntity.getQuantity(), true);

        orderEntity.setQuantity(0);

        orderRepository.save(orderEntity);

        log.debug("Order status updated successfully for " + orderEntity.getOrderId() + " to " + orderEntity.getOrderStatus());

        }return orderDTO.size() + " Orders returned successfully";
    } 
    @Transactional
    private void updateProductStock(String productId, int quantity, boolean isReturn) {
        if(isReturn) {
            productService.updateInventory(productId,quantity,true);
        } else {
            productService.updateInventory(productId,quantity,false);
        }
       
    }


    private OrderDTO ConvertToDTO(OrderEntity orderEntity) {
        // Implement logic to convert OrderEntity to OrderDTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setProduct(ProductService.ConvertToDTO(orderEntity.getProduct())); 
        orderDTO.setQuantity(orderEntity.getQuantity());
        orderDTO.setTotalPrice(orderEntity.getTotalPrice());
        orderDTO.setOrderStatus(orderEntity.getOrderStatus());

        return orderDTO;
    }

}
