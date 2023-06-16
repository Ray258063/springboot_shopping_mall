package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);

}
