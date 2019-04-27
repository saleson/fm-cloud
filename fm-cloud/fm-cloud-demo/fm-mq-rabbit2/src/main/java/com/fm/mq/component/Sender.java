package com.fm.mq.component;

import com.fm.mq.domain.Msg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void send(Msg msg) {
//        String context = "hello " + new Date();
        System.out.println("sender : " + msg);
        this.rabbitTemplate.convertAndSend("hello", msg);
    }


}
