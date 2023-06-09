package com.raychuang.springbootmail.dao.impl;

import com.raychuang.springbootmail.dao.ProductDao;
import com.raychuang.springbootmail.model.Product;
import com.raychuang.springbootmail.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
