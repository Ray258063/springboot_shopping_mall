package com.raychuang.springbootmail.dao;

import com.raychuang.springbootmail.model.Product;

public interface ProductDao {

    //根據productId 去資料庫查詢這個ID的商品數據出來
    Product getProductById(Integer productId);

}
