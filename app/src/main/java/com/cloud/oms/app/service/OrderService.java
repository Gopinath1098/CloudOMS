package com.cloud.oms.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cloud.oms.app.dto.OrderDetailsDTO;
import com.cloud.oms.app.dto.ProductDTO;
import com.cloud.oms.app.dto.ReturnOrderDTO;
import com.cloud.oms.app.notification.service.NotificationClient;
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

    private NotificationClient notificationClient;

    private OrderRepository orderRepository;

    private ProductService productService;


     public OrderService(OrderRepository orderRepository, ProductService productService, NotificationClient NotificationClient) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.notificationClient = NotificationClient;
    }

    public OrderDTO getOrderById(String id) {
        // Implement logic to retrieve order by ID
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return ConvertToDTO(orderEntity.orElseThrow(()->new OrderNotFoundException("Order not found with id: " + id)));
    }

    public List<OrderDTO> getOrderByState(OrderStatus state) {
        // Implement logic to retrieve order by state
        List<OrderEntity> orderEntities = (List<OrderEntity>) orderRepository.findByOrderStatus(state);
        if(orderEntities == null || orderEntities.isEmpty()) {
           throw new OrderNotFoundException("No Order Found with state: " + state);
        }
        return orderEntities.stream().map(this::ConvertToDTO).toList();
    }

    public String placeOrder(List<OrderDTO> orderDTO) {
        // Implement logic to place an order
        List<OrderEntity> savedOrders = null;
        for(OrderDTO order: orderDTO) {
            if(order==null||order.getQuantity() <= 0) {
                throw new OrderNotCreatedException("Invalid order details: Quantity must greater than zero");
            }
            ProductDTO productdto = productService.getProductById(order.getProductId());
            if(productdto==null) throw new OrderNotCreatedException("Invalid product details: give correct product id");
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setProduct(order.getProductId());
            orderEntity.setQuantity(order.getQuantity());
            orderEntity.setTotalPrice(order.getQuantity()*productdto.getProductPrice());
            orderEntity.setOrderStatus(order.getOrderStatus());
            orderEntity.setName(order.getName());
            orderEntity.setEmail(order.getEmail());
            orderEntity.setMobile_no(order.getMobile());
            OrderEntity savedOrder = orderRepository.save(orderEntity);
            if (savedOrders == null) savedOrders = new ArrayList<>();
            savedOrders.add(savedOrder);
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderEntity.getName(),orderEntity.getEmail(),orderEntity.getMobile_no());
            notify(orderDetailsDTO,orderEntity.getOrderId(),orderEntity.getOrderStatus());
            updateProductStock(productdto.getProductId(), order.getQuantity(), false);
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
      }else if(productService.getProductById(orderEntity.getProduct()).getProductStock() <= 0) {
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
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderEntity.getName(),orderEntity.getEmail(),orderEntity.getMobile_no());
        notify(orderDetailsDTO,orderEntity.getOrderId(),orderEntity.getOrderStatus());
      return  orderentity.getOrderStatus() + " Order updated successfully";
    }

    public String returnOrder(List<ReturnOrderDTO> orderDTO) {

        for(ReturnOrderDTO order: orderDTO) {
            OrderEntity orderEntity = orderRepository.findById(order.getOrderId()).orElseThrow(()->new OrderNotFoundException("Order not found with id: " + order.getOrderId()));

        if(orderEntity.getOrderStatus() == OrderStatus.DELIVERED) {
            orderEntity.setOrderStatus(OrderStatus.RETURNED);
        } else {
            throw new OrderNotFoundException("Order with id: " + order.getOrderId() + " cannot be returned from status: " + orderEntity.getOrderStatus());
        }
        String productId = orderEntity.getProduct();
        if(orderEntity.getQuantity()!=order.getQuantity()) throw new OrderNotFoundException("Order with id: " + order.getOrderId() + " Expected Return quantity: " + orderEntity.getQuantity());
        updateProductStock(productId, orderEntity.getQuantity(), true);

        orderRepository.save(orderEntity);

        log.debug("Order status updated successfully for " + orderEntity.getOrderId() + " to " + orderEntity.getOrderStatus());
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderEntity.getName(),orderEntity.getEmail(),orderEntity.getMobile_no());
        notify(orderDetailsDTO,orderEntity.getOrderId(),orderEntity.getOrderStatus());
        }return orderDTO.size() + " Orders returned successfully";
    } 
    @Transactional(rollbackFor = Exception.class)
    private void updateProductStock(String productId, int quantity, boolean isReturn) {
        if(isReturn) {
            productService.updateInventory(productId,quantity,true);
        } else {
            productService.updateInventory(productId,quantity,false);
        }
       
    }

    public boolean updateOrderQuantity(String orderId,int quantity){
         if(quantity<=0)  throw new OrderNotCreatedException("Invalid order details: Quantity must greater than zero");
         Optional<OrderEntity> order = orderRepository.findById(orderId);
         if(order.isEmpty()) throw new OrderNotFoundException("Order not found with id: " + orderId);
         ProductDTO productDTO = productService.getProductById(order.get().getProduct());
         log.info(String.valueOf(productDTO));
         updateProductStock(productDTO.getProductId(),quantity,false);
         OrderEntity orderEntity = order.get();
         orderEntity.setQuantity(order.get().getQuantity() + quantity);
         orderEntity.setTotalPrice(orderEntity.getQuantity()*productDTO.getProductPrice());
         orderRepository.save(orderEntity);
         return true;
    }

    public void deleteOrder(String orderId){
         Optional<OrderEntity> order = orderRepository.findById(orderId);
         order.orElseThrow(()->new OrderNotFoundException("Order not found with id: " + orderId));
         orderRepository.deleteById(orderId);
         notify(new OrderDetailsDTO(order.get().getName(),order.get().getEmail(),order.get().getMobile_no()),order.get().getOrderId(),order.get().getOrderStatus());
    }

    public void notify(OrderDetailsDTO orderDetailsDTO,String orderId,OrderStatus status){
        notificationClient.sendOrderNotification(orderDetailsDTO,status,orderId);
    }


    private OrderDTO ConvertToDTO(OrderEntity orderEntity) {
        // Implement logic to convert OrderEntity to OrderDTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setProductId(orderEntity.getProduct());
        orderDTO.setQuantity(orderEntity.getQuantity());
        orderDTO.setTotalPrice(orderEntity.getTotalPrice());
        orderDTO.setOrderStatus(orderEntity.getOrderStatus());

        return orderDTO;
    }

}
