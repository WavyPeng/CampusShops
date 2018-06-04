package com.wavy.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 解析路由并转发到相应的html中
 * Created by WavyPeng on 2018/6/4.
 */
@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        // 转发至店铺注册/编辑页面
        return "shop/shopoperation";
    }
}