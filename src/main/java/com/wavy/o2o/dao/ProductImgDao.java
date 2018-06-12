package com.wavy.o2o.dao;

import com.wavy.o2o.entity.ProductImg;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/12.
 */
public interface ProductImgDao {
    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);
}