package com.raychuang.springbootmail.dto;

import com.raychuang.springbootmail.constant.ProductCategory;

//借接住前端傳進來的參數
public class ProductQueryParams {
    private ProductCategory productCategory;
    private String search;
    private String orderBy;
    private String sort;


    public ProductCategory getProductCategory() {
        return productCategory;
    }


    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
