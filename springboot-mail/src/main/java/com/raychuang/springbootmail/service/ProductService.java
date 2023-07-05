package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.constant.ProductCategory;
import com.raychuang.springbootmail.dto.ProductQueryParams;
import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);


    //如果前端有10幾個參數要傳遞到後端用下面的方式會使得程式不好維護
    //List<Product> getProducts(ProductCategory productCategory, String search);
    //因此寫一個物件 然後設定 變數的getter and setter
    List<Product> getProducts(ProductQueryParams productQueryParams);

    Integer countProduct(ProductQueryParams productQueryParams);




}
