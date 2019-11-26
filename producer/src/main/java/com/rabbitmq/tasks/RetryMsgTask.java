package com.rabbitmq.tasks;

import com.rabbitmq.constant.Constant;
import com.rabbitmq.dao.BrokerMsgLogMapper;
import com.rabbitmq.entity.BrokerMsgLog;
import com.rabbitmq.entity.Orders;
import com.rabbitmq.producer.OrderSender;
import com.rabbitmq.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RetryMsgTask {

    @Autowired
    private OrderSender orderSender;

    @Autowired
    private BrokerMsgLogMapper brokerMsgLogMapper;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void retry() {
        System.out.println("-----------------定时任务启动------------------------");
        List<BrokerMsgLog> allLog = brokerMsgLogMapper.getAllByStatusAndOut();
        for (BrokerMsgLog log : allLog) {
            if (log.getTryCount() <= 3) {
                //重发
                Orders orders = (Orders) JsonUtil.JSON2Object(log.getMessage());
                try {
                    orderSender.sender(orders);
                    brokerMsgLogMapper.retryAddOne(log.getMsgId());
                } catch (Exception e) {
                    System.err.println("------------------订单重发-------------");
                    e.printStackTrace();
                }
            } else {
                //修改状态为3
                brokerMsgLogMapper.updateOrderStatus(log.getMsgId(), Constant.ORDER_SEND_FAIL, new Date());
                System.err.println("------------------订单发送失败-------------");
            }
        }
    }
}
