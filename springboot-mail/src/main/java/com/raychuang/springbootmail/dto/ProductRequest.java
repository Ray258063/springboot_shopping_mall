package com.raychuang.springbootmail.dto;

import com.raychuang.springbootmail.constant.ProductCategory;

import javax.validation.constraints.NotNull;

//前端傳入商品數據 轉入資料庫

public class ProductRequest {
    @NotNull
    private String productName;
    //使用String類型來存category有一個缺點 就是我們無法在程式中就知道有哪幾個類別
    //private String category;
    @NotNull
    private String category;
    @NotNull
    private String image_url;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    private String description;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
