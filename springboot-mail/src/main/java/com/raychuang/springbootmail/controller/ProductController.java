package com.raychuang.springbootmail.controller;

import com.raychuang.springbootmail.constant.ProductCategory;
import com.raychuang.springbootmail.dto.ProductQueryParams;
import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.service.ProductService;
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
@Validated //才能使用 Min and Max 註解
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
    public ResponseEntity<Page<Product>>getProducts(
            //productCategory不是必要參數 沒有的話就查詢全部的資料
            //(1) 根據類別查詢
            //(2) 根據打的字查詢
            //# 查詢條件 filtering
            @RequestParam(required = false) ProductCategory productCategory,
            @RequestParam(required = false) String search,
            //根據什麼欄位來排序 例如商品價格 商品創建時間
            //# 排序 sorting
            @RequestParam(defaultValue = "created_date") String orderBy, //如果前端沒有傳遞參數進來 orderBy 就是預設的create_date
            //選則升冪 or 降冪
            @RequestParam(defaultValue = "desc") String sort, //預設使用降冪排序
            // #分頁 pagination
            // 對應到 sql 語句的limit and offset 限制 以及 跳過幾筆
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, //設定前端傳入的範圍
            @RequestParam (defaultValue = "0") @Min(0) Integer offset //分頁 假如第二頁的5筆 就是 offset=5
            ){
        //將前端傳進來的參數set到ProductQueryParams裡
        ProductQueryParams productQueryParams=new ProductQueryParams();
        productQueryParams.setProductCategory(productCategory);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得 product list

        List<Product> productList= productService.getProducts(productQueryParams);
        //取得 product 總數
        Integer total=productService.countProduct(productQueryParams);

        //分頁
        Page page=new Page();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }




}
