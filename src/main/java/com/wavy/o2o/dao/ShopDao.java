package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Shop;

/**
 * Created by WavyPeng on 2018/6/1.
 */
public interface ShopDao {
    /**
     * 添加商铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 修改商铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过shop Id查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
}
