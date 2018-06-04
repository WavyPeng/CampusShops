package com.wavy.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavy.o2o.dao.ShopDao;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ShopDto;
import com.wavy.o2o.entity.Area;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.ShopCategory;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.enums.ShopStateEnum;
import com.wavy.o2o.exception.ShopOperationException;
import com.wavy.o2o.service.IAreaService;
import com.wavy.o2o.service.IShopCategoryService;
import com.wavy.o2o.service.IShopService;
import com.wavy.o2o.util.CodeUtil;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WavyPeng on 2018/6/4.
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IShopCategoryService shopCategoryService;
    @Autowired
    private IAreaService areaService;

    @RequestMapping("/registerShop")
    @ResponseBody
    public Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        // 1.从前端接收并转化相应的参数，包括店铺信息及图片信息
        String shopStr = TypeUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (IOException ioe){
            modelMap.put("success",false);
            modelMap.put("errMsg",ioe.getMessage());
            return modelMap;
        }
        // 处理上传的图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        // 2.注册店铺
        if(shop!=null && shopImg!=null){
            // 用户信息应该是由session来获取的，这里是为了测试
            UserInfo user = new UserInfo();
            user.setUserId(1L);
            shop.setOwner(user);
            ShopDto shopDto = null;
            try{
                shopDto = shopService.addShop(shop, new ImageDto(shopImg.getOriginalFilename(),shopImg.getInputStream()));
                if(shopDto.getState() == ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",shopDto.getStateInfo());
                }
            }catch (IOException ioe){
                modelMap.put("success",false);
                modelMap.put("errMsg",ioe.getMessage());
            }catch (ShopOperationException soe){
                modelMap.put("success",false);
                modelMap.put("errMsg",soe.getMessage());
            }
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
}