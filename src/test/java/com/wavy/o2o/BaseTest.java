package com.wavy.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动时加载spring IOC容器
 * Created by WavyPeng on 2018/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告知junit spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-redis.xml"})
public class BaseTest {

}
