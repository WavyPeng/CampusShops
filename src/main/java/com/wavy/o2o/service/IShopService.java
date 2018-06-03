package com.wavy.o2o.service;

import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ShopDto;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.exception.ShopOperationException;

/**
 * Created by WavyPeng on 2018/6/3.
 */
public interface IShopService {
    /**
     * 注册店铺
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopDto addShop(Shop shop, ImageDto thumbnail)throws ShopOperationException;
}
