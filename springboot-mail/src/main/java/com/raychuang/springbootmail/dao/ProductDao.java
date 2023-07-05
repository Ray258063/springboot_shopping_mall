package com.raychuang.springbootmail.dao;

import com.raychuang.springbootmail.constant.ProductCategory;
import com.raychuang.springbootmail.dto.ProductQueryParams;
import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;

import java.util.List;

public interface ProductDao {

    //根據productId 去資料庫查詢這個ID的商品數據出來
    Product getProductById(Integer productId);
    //根據productRequest 創建ㄧ筆商品數據
    Integer createProduct(ProductRequest productRequest);
    //根據商品id 以及 request body 去修改商品數據
    void updateProduct(Integer productId, ProductRequest productRequest);
    //根據商品id  去刪除商品數據
    void deleteProduct(Integer productId);
    //根據前端傳入的參數取得商品資訊
    List<Product> getProducts(ProductQueryParams productQueryParams);
    //計算
    Integer countProduct(ProductQueryParams productQueryParams);
    //更新商品庫存
    void updateStock(Integer productId, Integer stock);

}
