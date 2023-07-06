package com.raychuang.springbootmail.service.impl;

import com.raychuang.springbootmail.dao.OrderDao;
import com.raychuang.springbootmail.dao.ProductDao;
import com.raychuang.springbootmail.dao.UserDao;
import com.raychuang.springbootmail.dto.BuyItem;
import com.raychuang.springbootmail.dto.CreateOrderRequest;
import com.raychuang.springbootmail.dto.OrderQueryParams;
import com.raychuang.springbootmail.model.Order;
import com.raychuang.springbootmail.model.OrderItem;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.model.User;
import com.raychuang.springbootmail.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    private final static Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);


    @Transactional //有修改多張table時記得要加上這個註解
    @Override
    public Integer creatdOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        //檢查user是否存在
        User user=userDao.getUserById(userId);
        if(user==null){
            log.warn("該 userId {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //計算總價錢
        int totalAmount=0;
        //用一個list來存放商品詳細資訊
        List<OrderItem> orderItemList=new ArrayList<>();

        for(BuyItem buyItem: createOrderRequest.getBuyItemList()){// 跑回圈看使用者下單了什麼
            //先到資料庫搜尋此商品的詳細數據
            Product product=productDao.getProductById(buyItem.getProductId()); //根據下單的東西取的他的id再到productDao查這個商品的詳細數據
            if(product==null){
                log.warn("此商品 {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if(product.getStock()<buyItem.getQuantity()){
                log.warn("此商品 {} 庫存量不足，無法購買。剩餘庫存 {}，欲購買數量 {}",buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //更新庫存
            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());

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

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        //訂單
        List<Order> orderList=orderDao.getOrders(orderQueryParams);

        //訂單詳細訂購的品項
        for(Order order: orderList){
            List<OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {

        return orderDao.countOrders(orderQueryParams);
    }
}

