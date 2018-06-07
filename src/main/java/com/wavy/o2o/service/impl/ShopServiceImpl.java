package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.ShopDao;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ShopDto;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.enums.ShopStateEnum;
import com.wavy.o2o.exception.ShopOperationException;
import com.wavy.o2o.service.IShopService;
import com.wavy.o2o.util.ImageUtil;
import com.wavy.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by WavyPeng on 2018/6/3.
 */
@Service
public class ShopServiceImpl implements IShopService{

    @Autowired
    private ShopDao shopDao;

    /**
     * 注册店铺
     * @param shop
     * @param thumbnail
     * @return
     */
    @Transactional
    @Override
    public ShopDto addShop(Shop shop, ImageDto thumbnail) throws ShopOperationException{
        if(shop == null){
            return new ShopDto(ShopStateEnum.NULL_SHOP);
        }
        try {
            // 给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            // 添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    // 存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("添加店铺图片出错：" + e.getMessage());
                    }
                    // 更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("添加店铺出错：" + e.getMessage());
        }
        // 添加成功，审核中
        return new ShopDto(ShopStateEnum.CHECK, shop);
    }

    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     */
    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    @Override
    @Transactional
    public ShopDto modifyShop(Shop shop, ImageDto thumbnail) throws ShopOperationException {
        if(shop == null || shop.getShopId()==null){
            return new ShopDto(ShopStateEnum.NULL_SHOP);
        }else{
            try{
                // 1.判断是否需要修改图片
                if(thumbnail.getImage()!=null
                        &&thumbnail.getImageName()!=null
                        &&!"".equals(thumbnail.getImageName())){
                    Shop tmpShop = shopDao.queryByShopId(shop.getShopId());
                    // 如果原纪录中已存在图片，则将其删除
                    if(tmpShop.getShopImg()!=null){
                        ImageUtil.deleteFileOrPath(tmpShop.getShopImg());
                    }
                    // 更新图片
                    addShopImg(shop,thumbnail);
                }
                // 2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopDto(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopDto(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }

    private void addShopImg(Shop shop, ImageDto thumbnail) {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }
}
