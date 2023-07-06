package com.raychuang.springbootmail.dao;

import com.raychuang.springbootmail.dto.OrderQueryParams;
import com.raychuang.springbootmail.model.Order;
import com.raychuang.springbootmail.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
    //取得訂單總資訊
    Order getOrderById(Integer orderId);

    //一張訂單裡面可能有很多的商品所以使用list
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrders(OrderQueryParams orderQueryParams);

}
