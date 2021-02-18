package com.jiuzhang.seckill.db.dao;

import com.jiuzhang.seckill.db.mappers.OrderMapper;
import com.jiuzhang.seckill.db.po.Order;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

public interface OrderDao {
    void insertOrder(Order order);
    Order queryOrder(String orderNo);
    void updateOrder(Order order);
}

