package com.wavy.o2o.service;

import com.wavy.o2o.BaseTest;
import com.wavy.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by WavyPeng on 2018/6/1.
 */
public class AreaServiceTest extends BaseTest{
    @Autowired
    private IAreaService areaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("西苑",areaList.get(0).getAreaName());
    }
}
