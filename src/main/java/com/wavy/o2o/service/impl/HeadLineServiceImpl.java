package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.HeadLineDao;
import com.wavy.o2o.entity.HeadLine;
import com.wavy.o2o.service.IHeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements IHeadLineService{

    @Autowired
    private HeadLineDao headLineDao;

    /**
     * 根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     */
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}