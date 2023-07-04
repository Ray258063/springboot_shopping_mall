package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.dto.CreateOrderRequest;

public interface OrderService {

    Integer creatdOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
