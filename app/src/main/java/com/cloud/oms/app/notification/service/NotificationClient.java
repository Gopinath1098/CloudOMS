package com.cloud.oms.app.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.oms.app.Status.OrderStatus;
import com.cloud.oms.app.notification.dto.OrderCreatedEvent;

@Service
public class NotificationClient {

    private final RestTemplate restTemplate;

    public NotificationClient(
            RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public void sendOrderNotification(
            OrderCreatedEvent event,OrderStatus status,String orderId) {

        String url = "http://localhost:8082/notifications/order-created"
           + "?status=" + status.toString()
           + "&orderId=" + orderId;

        restTemplate.postForObject(
            url,
            event,
            String.class
        );
    }
}
