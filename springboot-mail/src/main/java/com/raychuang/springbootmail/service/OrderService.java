package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.dto.CreateOrderRequest;
import com.raychuang.springbootmail.model.Order;

public interface OrderService {

    //創建訂單
    Integer creatdOrder(Integer userId, CreateOrderRequest createOrderRequest);
    //
    Order getOrderById(Integer orderId);
}
