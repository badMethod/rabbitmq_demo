package com.rabbitmq.service;

import com.rabbitmq.entity.Orders;


public interface OrderService {
    Orders get(String id);

    void sendOrder(Orders order) throws Exception;
}
