package com.fm.mq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessagingMessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitConfig implements ApplicationContextAware {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public MessagingMessageConverter messagingMessageConverter() {
        return new MessagingMessageConverter();
    }


    //    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(new Queue("hello"));
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认

//        MessagingMessageConverter converter = messagingMessageConverter();
        container.setMessageConverter(new Jackson2JsonMessageConverter());
        container.setMessageListener(new ChannelAwareMessageListener() {


            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("Listener onMessage : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
                message.getMessageProperties().setInferredArgumentType(Class.forName((String) message.getMessageProperties().getHeaders().get("__TypeId__")));
                Object convertObject = container.getMessageConverter().fromMessage(message);
                System.out.println(convertObject);
            }
        });
        return container;
    }


    @Autowired
    private ConnectionFactory connectionFactory;
    private ConfigurableApplicationContext ctx;

    @PostConstruct
    public void init() {
        ctx.getBeanFactory().registerSingleton("messageContainer", messageContainer(connectionFactory));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.ctx = (ConfigurableApplicationContext) applicationContext;
        }
    }
}
