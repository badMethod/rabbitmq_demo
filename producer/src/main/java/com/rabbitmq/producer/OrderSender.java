package com.rabbitmq.producer;

import com.rabbitmq.constant.Constant;
import com.rabbitmq.dao.BrokerMsgLogMapper;
import com.rabbitmq.entity.Orders;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMsgLogMapper brokerMsgLogMapper;

    final RabbitTemplate.ConfirmCallback confirmcall = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.err.println("-----------回调成功" + correlationData);
            String msgId = correlationData.getId();
            if (ack) {
                brokerMsgLogMapper.updateOrderStatus(msgId, Constant.ORDER_SEND_SUCCESS, new Date());
            } else {
                System.err.println("出现异常");
            }
        }
    };

    public void sender(Orders order) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmcall);
        CorrelationData correlationData = new CorrelationData(order.getMsgId());
        rabbitTemplate.convertAndSend("order-exchange", "order.abc", order, correlationData);
    }
}
