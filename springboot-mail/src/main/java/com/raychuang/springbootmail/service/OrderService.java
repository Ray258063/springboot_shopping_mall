package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.dto.CreateOrderRequest;
import com.raychuang.springbootmail.dto.OrderQueryParams;
import com.raychuang.springbootmail.model.Order;

import java.util.List;

public interface OrderService {

    //創建訂單
    Integer creatdOrder(Integer userId, CreateOrderRequest createOrderRequest);
    //
    Order getOrderById(Integer orderId);

    //取得訂單
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    //計算訂單的數量
    Integer countOrders(OrderQueryParams orderQueryParams);

}
