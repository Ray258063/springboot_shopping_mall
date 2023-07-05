package com.raychuang.springbootmail.service.impl;

import com.raychuang.springbootmail.dao.OrderDao;
import com.raychuang.springbootmail.dao.ProductDao;
import com.raychuang.springbootmail.dto.BuyItem;
import com.raychuang.springbootmail.dto.CreateOrderRequest;
import com.raychuang.springbootmail.model.Order;
import com.raychuang.springbootmail.model.OrderItem;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;


    @Transactional //有修改多張table時記得要加上這個註解
    @Override
    public Integer creatdOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //計算總價錢
        int totalAmount=0;
        //用一個list來存放商品詳細資訊
        List<OrderItem> orderItemList=new ArrayList<>();

        for(BuyItem buyItem: createOrderRequest.getBuyItemList()){// 跑回圈看使用者下單了什麼
            Product product=productDao.getProductById(buyItem.getProductId()); //根據下單的東西取的他的id再到productDao查這個商品的詳細數據
            int amount=buyItem.getQuantity()*product.getPrice(); //取得使用者下單的數量 和 到product裡獲得價格
            totalAmount=totalAmount+amount; //使用者可能下單了很多品項的商品 一一的將價格加總起來

            //轉換BuyItem to OrderItem
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //call Dao層 創建 order and orderItem table
        Integer orderId=orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        //根據orderId去取得那筆訂單
        Order order=orderDao.getOrderById(orderId);
        //根據orderId 去取得那筆訂單的所有品項
        List<OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(orderId);
        //將orderItemList的數據加到order裡面
        order.setOrderItemList(orderItemList);
        //一張order裡包含多個orderitem

        return order;
    }
}

