package com.cloud.oms.app.dto;

import com.cloud.oms.app.Status.OrderStatus;


public class OrderDTO {
    private String orderId;
    private ProductDTO product;
    private int quantity;
    private double totalPrice;
    private OrderStatus orderStatus;

    public OrderDTO() {}

    public OrderDTO(String orderId, ProductDTO product, int quantity, double totalPrice, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public ProductDTO getProduct() { return product; }
    public void setProduct(ProductDTO product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
