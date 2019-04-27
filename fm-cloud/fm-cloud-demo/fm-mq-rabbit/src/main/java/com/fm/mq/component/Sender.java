package com.fm.mq.component;

import com.fm.mq.domain.Msg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private Source output;

    public void send(Msg msg) {
//        String context = "hello " + new Date();
        System.out.println("sender : " + msg);
        this.rabbitTemplate.convertAndSend("hello", msg);
    }


    public void sendOutput(Msg msg) {
        System.out.println("sendOutput : " + msg);
        output.output().send(MessageBuilder.withPayload(msg).build());
    }
}
