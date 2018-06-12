package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Product;

/**
 * Created by WavyPeng on 2018/6/12.
 */
public interface ProductDao {
    /**
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);
}