package com.raychuang.springbootmail.controller;

import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    //查詢商品
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    //新增商品
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId=productService.createProduct(productRequest);
        Product product=productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //修改商品數據
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updataProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        //檢查Product是否存在
        Product product=productService.getProductById(productId);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //修改商品數據
        productService.updateProduct(productId,productRequest);
        Product updateproduct=productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateproduct);

    }

    //刪除商品
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
