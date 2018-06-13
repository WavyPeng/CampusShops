package com.wavy.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by WavyPeng on 2018/6/13.
 */
@RequestMapping("/frontend")
@Controller
public class FrontendController {
    /**
     * 首页路由
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }
}