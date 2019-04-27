package com.fm.test.mq;

import com.fm.mq.MqRabbitApplication;
import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@ActiveProfiles({"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqRabbitApplication.class)
public class RabbitClientTest {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;

    private String exchangeName = "exchangeTest1";
    private String queueName = "queueName1";
    private String routingKey = "ld";
    private Channel providerChannel;
    private Channel consumerChannel;


    @Before
    public void initProvider() throws IOException {
        providerChannel = connectionFactory.createConnection().createChannel(false);
        providerChannel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
        initConsumer();
    }


    //    @Before
    public void initConsumer() throws IOException {
        consumerChannel = connectionFactory.createConnection().createChannel(false);


        consumerChannel.queueDeclare(queueName, true, false, false, null);
        consumerChannel.queueBind(queueName, exchangeName, routingKey);
        // 同一时刻服务器只会发一条消息给消费者
        consumerChannel.basicQos(1);
        consumerChannel.basicConsume(queueName, false, new Consumer() {

            @Override
            public void handleConsumeOk(String consumerTag) {
                System.out.println("#handleConsumeOk():" + consumerTag);
            }

            @Override
            public void handleCancelOk(String consumerTag) {
                System.out.println("#handleCancelOk():" + consumerTag);
            }

            @Override
            public void handleCancel(String consumerTag) throws IOException {
                System.out.println("#handleCancel():" + consumerTag);
            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                System.out.println("#handleShutdownSignal():" + consumerTag);
            }

            @Override
            public void handleRecoverOk(String consumerTag) {
                System.out.println("#handleRecoverOk():" + consumerTag);
            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("#handleDelivery():" + consumerTag);
                consumerChannel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }


    @Test
    public void test() throws IOException {
        String message = "删除商品，id = 1001";
        providerChannel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }
}
