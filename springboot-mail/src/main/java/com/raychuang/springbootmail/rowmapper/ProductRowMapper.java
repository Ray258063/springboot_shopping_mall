package com.raychuang.springbootmail.rowmapper;

import com.raychuang.springbootmail.constant.ProductCategory;
import com.raychuang.springbootmail.model.Product;
import org.springframework.jdbc.core.RowMapper;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;


//要轉換成Product這個class
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product=new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        //資料庫取出來的是String 類型 所以會報錯
        //product.setCategory(resultSet.getString("category"));
        //(1)取出字串
        String categoryStr=resultSet.getString("category");
        //(2)轉 Enum
        ProductCategory category=ProductCategory.valueOf(categoryStr);
        //(3)set進去
        //(4)快速的寫法 product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));
        product.setCategory(category);
        product.setImage_url(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return product;
    }
}
