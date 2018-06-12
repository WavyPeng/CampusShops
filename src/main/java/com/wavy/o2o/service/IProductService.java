package com.wavy.o2o.service;

import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ProductDto;
import com.wavy.o2o.entity.Product;
import com.wavy.o2o.exception.ProductOperationException;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/12.
 */
public interface IProductService {
    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     */
    ProductDto addProduct(Product product, ImageDto thumbnail,List<ImageDto> productImgList)
            throws ProductOperationException;
}