package com.raychuang.springbootmail.constant;

public class test {
    public static void main(String[] args) {
        ProductCategory productCategory=ProductCategory.FOOD;
        System.out.println(productCategory);
        String s=productCategory.name();
        System.out.println(s);

        String s2="CAR";
        ProductCategory s3=ProductCategory.valueOf(s2);
        System.out.println(s3);
    }
}
