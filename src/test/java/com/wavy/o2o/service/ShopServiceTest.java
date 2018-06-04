package com.wavy.o2o.service;

import com.wavy.o2o.BaseTest;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ShopDto;
import com.wavy.o2o.entity.Area;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.ShopCategory;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.enums.ShopStateEnum;
import com.wavy.o2o.exception.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by WavyPeng on 2018/6/3.
 */
public class ShopServiceTest extends BaseTest{
    @Autowired
    private IShopService shopService;

    @Test
    public void testAddShop() throws ShopOperationException,FileNotFoundException{
        Shop shop = new Shop();
        UserInfo owner = new UserInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("E:/o2o/image/xiaohuangren.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageDto imageDto = new ImageDto(shopImg.getName(),is);
        ShopDto se = shopService.addShop(shop,imageDto);
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }
}