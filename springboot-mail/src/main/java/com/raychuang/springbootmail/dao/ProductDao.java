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
    void deleteProduct(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);

}
