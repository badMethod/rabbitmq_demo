package com.rabbitmq.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.rabbitmq.constant.Constant;
import com.rabbitmq.dao.BrokerMsgLogMapper;
import com.rabbitmq.dao.OrderMapper;
import com.rabbitmq.entity.BrokerMsgLog;
import com.rabbitmq.entity.Orders;
import com.rabbitmq.producer.OrderSender;
import com.rabbitmq.service.OrderService;
import com.rabbitmq.utils.JsonUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Transactional(rollbackFor = Exception.class)
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSender orderSender;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BrokerMsgLogMapper brokerMsgLogMapper;

    @Override
    public Orders get(String id) {
        System.out.println("-----------------");
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public void sendOrder(Orders order) throws Exception {
        orderMapper.insert(order);
        Date now = new Date();
        BrokerMsgLog brokerMsgLog = new BrokerMsgLog();
        brokerMsgLog.setMsgId(order.getMsgId());
        brokerMsgLog.setStatus(Constant.ORDER_SENDING);
        brokerMsgLog.setMessage(JsonUtil.Object2JSON(order));
        brokerMsgLog.setTryCount(0);
        brokerMsgLog.setTimeOut(DateUtils.addMinutes(now, Constant.ORDER_OUT_TIME));
        brokerMsgLog.setCreateTime(now);
        brokerMsgLog.setUpdateTime(now);
        brokerMsgLogMapper.insert(brokerMsgLog);
        orderSender.sender(order);
    }
}
