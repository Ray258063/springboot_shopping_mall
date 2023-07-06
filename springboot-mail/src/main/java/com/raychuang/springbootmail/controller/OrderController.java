package com.raychuang.springbootmail.controller;

import com.raychuang.springbootmail.dto.CreateOrderRequest;
import com.raychuang.springbootmail.dto.OrderQueryParams;
import com.raychuang.springbootmail.model.Order;
import com.raychuang.springbootmail.service.OrderService;
import com.raychuang.springbootmail.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    //前端要創建訂單
    @PostMapping("/users/{userId}/orders") //所有的user中的其中ㄧ個user創建一筆訂單出來// 在其中一個user的下面創建一筆訂單出來
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        //創建訂單
        Integer orderId=orderService.creatdOrder(userId,createOrderRequest);
        //讀取已經創建的訂單
        Order order=orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }

    //前端要查詢訂單
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
                                                 @RequestParam(defaultValue = "0")  @Min(0) Integer offset){

        OrderQueryParams orderQueryParams=new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得order list
        List<Order> orderList =orderService.getOrders(orderQueryParams);
        //取得order的總數
        Integer count=orderService.countOrders(orderQueryParams);

        //分頁
        Page<Order> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResult(orderList);


        return ResponseEntity.status(HttpStatus.OK).body(page);


    }




}
