package com.raychuang.springbootmail.controller;

import com.raychuang.springbootmail.constant.ProductCategory;
import com.raychuang.springbootmail.dto.ProductQueryParams;
import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    //admin 操作 CRUD
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
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
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

    //N-user操作v1
    //取得所有商品資料
//    @GetMapping("/products")
//    public ResponseEntity<List<Product> >getProducts(){
//        List<Product> productList= productService.getProducts();
//        return ResponseEntity.status(HttpStatus.OK).body(productList);
//    }
    //N-user操作v2
    //取得所有商品資料
    //根據前端傳入的種類 進行查詢
    //根據前端傳入的關鍵字進行查詢
    @GetMapping("/products")
    public ResponseEntity<List<Product> >getProducts(
            //productCategory不是必要參數 沒有的話就查詢全部的資料
            //(1) 根據類別查詢
            //(2) 根據打的字查詢
            @RequestParam(required = false) ProductCategory productCategory,
            @RequestParam(required = false) String search
            ){
        //將前端傳進來的參數set到ProductQueryParams裡
        ProductQueryParams productQueryParams=new ProductQueryParams();
        productQueryParams.setProductCategory(productCategory);
        productQueryParams.setSearch(search);

        List<Product> productList= productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }




}
