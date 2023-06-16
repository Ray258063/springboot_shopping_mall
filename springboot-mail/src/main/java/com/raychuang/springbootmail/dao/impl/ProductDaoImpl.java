package com.raychuang.springbootmail.dao.impl;

import com.raychuang.springbootmail.dao.ProductDao;
import com.raychuang.springbootmail.dto.ProductRequest;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {

        String sql="SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id=:productId";
        Map map=new HashMap();
        map.put("productId",productId);

        List<Product> productlist = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(productlist.size()>0){
            return productlist.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql="INSERT INTO product(product_name,category,image_url,price," +
                "stock,description,created_date,last_modified_date )" +
                "VALUES (:productName,:category,:ImageUrl,:price,:stock,:description,:createDate,:lastModifiedDate)" ;

       Map<String, Object> map=new HashMap<>();
       map.put("productName",productRequest.getProductName());
       map.put("category",productRequest.getCategory());
       map.put("ImageUrl",productRequest.getImage_url());
       map.put("price",productRequest.getPrice());
       map.put("stock",productRequest.getStock());
       map.put("description",productRequest.getDescription());
       Date now=new Date();
       map.put("createDate",now);
       map.put("lastModifiedDate",now);
       KeyHolder keyHolder=new GeneratedKeyHolder();


       namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
       int productId=keyHolder.getKey().intValue();

       return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql="UPDATE product set product_name=:productName, category=:category, image_url=:imageUrl, price=:price," +
                "stock=:stock, description=:description, last_modified_date=:lastModifiedDate " +
                "WHERE product_id=:productId";

        Map<String,Object> map=new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImage_url());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql="DELETE FROM product WHERE product_id=:productId";
        Map<String,Object> map=new HashMap<>();
        map.put("productId",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
