package com.rabbitmq.conmuser;

import com.rabbitmq.client.Channel;
import com.rabbitmq.entity.Orders;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderConsumer {

    //    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
//                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
//            exchange = @Exchange(name = "${spring.rabbitmq.listener.order.exchange.name}",
//                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
//                    type = "${spring.rabbitmq.listener.order.exchange.type}",
//                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
//            key = "${spring.rabbitmq.listener.order.key}"
//    ))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue",
                    durable = "true"),
            exchange = @Exchange(name = "order-exchange",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "order.*"
    ))
    @RabbitHandler
    public void receive(@Payload Orders order, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        System.err.println("-----------------------------收到订单-----------");
        System.err.println("------------" + order.getId() + "--------" + order.getName());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        channel.basicAck(deliveryTag, false);
    }
}
