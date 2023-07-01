package com.raychuang.springbootmail.dao.impl;

import com.raychuang.springbootmail.constant.ProductCategory;
import com.raychuang.springbootmail.dao.ProductDao;
import com.raychuang.springbootmail.dto.ProductQueryParams;
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

        String sql="SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date " +
                "FROM product " +
                "WHERE product_id=:productId";

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
        String sql="UPDATE product set product_name=:productName, category=:category, image_url=:imageUrl, " +
                "price=:price," +
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

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql="SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String,Object> map=new HashMap<>();
        //查詢條件
        sql=addFilteringSql(sql,map,productQueryParams);
        //排序
        //拼接sql語句記得在前後都要保留空白
        sql=sql+" ORDER BY "+productQueryParams.getOrderBy()+" "+productQueryParams.getSort();

        //分頁
        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql="SELECT count(*) FROM product WHERE 1=1";
        Map<String,Object> map=new HashMap<>();
        //查詢條件
        sql=addFilteringSql(sql,map,productQueryParams);
        //用來取count的時候
        //將count的值轉成 Integer的返回值
        Integer total=namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return total;
    }

    private String addFilteringSql(String sql, Map<String,Object> map,ProductQueryParams productQueryParams){
        //查詢條件
        //假如前端有category參數才去使用根據category下sql語句
        if(productQueryParams.getProductCategory()!=null){
            sql=sql+" AND category=:category";
            map.put("category",productQueryParams.getProductCategory().name());
        }
        if(productQueryParams.getSearch()!=null){
            //%不能寫在sql語句裡 必須寫在map
            sql=sql+" AND product_name LIKE :search";
            map.put("search","%"+productQueryParams.getSearch()+"%");
        }
        return sql;
    }
}
