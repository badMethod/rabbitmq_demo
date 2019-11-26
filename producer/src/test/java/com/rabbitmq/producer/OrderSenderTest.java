package com.rabbitmq.producer;

import com.rabbitmq.dao.BrokerMsgLogMapper;
import com.rabbitmq.entity.BrokerMsgLog;
import com.rabbitmq.entity.Orders;
import com.rabbitmq.service.OrderService;
import com.rabbitmq.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@SpringJUnitConfig
@SpringBootTest
class OrderSenderTest {

    @Autowired
    private OrderSender orderSender;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BrokerMsgLogMapper brokerMsgLogMapper;

    @Test
    public void test1() throws Exception {
        Orders order = new Orders();
        order.setId("4");
        order.setMsgId(System.currentTimeMillis() + "&" + UUID.randomUUID());
        order.setName("黄大发客户4");
//        String json = JsonUtil.Object2JSON(order);
//        System.out.println(json);
//        Orders o = (Orders) JsonUtil.JSON2Object(json);
//        System.out.println(o.getName());
        orderService.sendOrder(order);
//        orderSender.sender(order);
    }

    @Test
    public void test2() {
        Orders order = orderService.get("1");
        System.out.println(order.getName());
    }

    @Test
    public void test3() {
//        brokerMsgLogMapper.updateOrderStatus("1", "2", new Date());
//        List<BrokerMsgLog> allByStatusAndOut = brokerMsgLogMapper.getAllByStatusAndOut();
//        allByStatusAndOut.forEach(brokerMsgLog -> {
//            System.out.println(brokerMsgLog.getMsgId());
//        });
//        for (BrokerMsgLog brokerMsgLog: allByStatusAndOut) {
//            System.out.println(brokerMsgLog.getMsgId());
//        }
        brokerMsgLogMapper.retryAddOne("1");
    }


}