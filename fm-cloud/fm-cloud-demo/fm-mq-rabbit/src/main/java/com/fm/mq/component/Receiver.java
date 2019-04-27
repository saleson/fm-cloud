package com.fm.mq.component;

import com.fm.mq.domain.Msg;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

//@Component
@RabbitListener(queues = "hello", containerFactory = "rabbitListenerContainerFactory")
public class Receiver {

//    @RabbitHandler
//    public void process(Object hello, Channel channelToUse) {
//        long deliveryTag = ((Message) hello).getMessageProperties().getDeliveryTag();
//        try {
//            channelToUse.basicAck(deliveryTag, false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Receiver : " + hello);
//    }


    @RabbitHandler
    public void process(@Payload Msg msg, Message message, Channel channelToUse) {
        System.out.println("receiver:" + msg);
//        try {
//            channelToUse.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
