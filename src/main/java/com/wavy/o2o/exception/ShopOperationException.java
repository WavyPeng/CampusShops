package com.wavy.o2o.exception;

/**
 * 店铺操作异常类
 * 继承RuntimeException是为了事务回滚
 * Created by WavyPeng on 2018/6/3.
 */
public class ShopOperationException extends RuntimeException{
    public ShopOperationException(String msg) {
        super(msg);
    }
}
