package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.AreaDao;
import com.wavy.o2o.entity.Area;
import com.wavy.o2o.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/1.
 */
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private AreaDao areaDao;

    /**
     * 获取区域列表
     * @return
     */
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
