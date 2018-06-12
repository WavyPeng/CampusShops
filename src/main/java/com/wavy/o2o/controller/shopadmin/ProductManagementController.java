package com.wavy.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ProductDto;
import com.wavy.o2o.entity.Product;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.enums.ProductStateEnum;
import com.wavy.o2o.exception.ProductOperationException;
import com.wavy.o2o.service.IProductService;
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
 * Created by WavyPeng on 2018/6/12.
 */
@RequestMapping("/shopadmin")
@Controller
public class ProductManagementController {
    @Autowired
    private IProductService productService;

    // 支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageDto thumbnail = null;
        List<ImageDto> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try{
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        try{
            // 获取前端传过来的表单string流并将其转换成Product实体类
            String productStr = TypeUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        }catch (Exception e){
            System.out.println(e.toString());
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        // 若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if(product!=null && thumbnail!=null && productImgList.size()>0){
            try{
                // 从session中获取当前店铺信息
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductDto pd = productService.addProduct(product,thumbnail,productImgList);
                if(pd.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pd.getStateInfo());
                }
            }catch (ProductOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private ImageDto handleImage(HttpServletRequest request, ImageDto thumbnail, List<ImageDto> productImgList)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageDto(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        // 取出详情图列表并构建List<ImageDto>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageDto productImg = new ImageDto(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                // 若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }
}