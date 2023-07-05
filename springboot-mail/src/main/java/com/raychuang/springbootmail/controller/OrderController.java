package com.raychuang.springbootmail.controller;

import com.raychuang.springbootmail.dto.CreateOrderRequest;
import com.raychuang.springbootmail.model.Order;
import com.raychuang.springbootmail.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders") //所有的user中的其中ㄧ個user創建一筆訂單出來// 在其中一個user的下面創建一筆訂單出來
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        //創建訂單
        Integer orderId=orderService.creatdOrder(userId,createOrderRequest);
        //讀取已經創建的訂單
        Order order=orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }


}
