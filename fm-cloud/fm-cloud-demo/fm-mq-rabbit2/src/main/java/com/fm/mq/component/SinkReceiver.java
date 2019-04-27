package com.fm.mq.component;


import com.fm.seceiver.AutoReceiver;
import com.fm.seceiver.MudlReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "spring.cloud.stream.bindings.TestInput.enable", havingValue = "true")
@EnableBinding({Source.class})
public class SinkReceiver {


    private static Logger log = LoggerFactory.getLogger(SinkReceiver.class);

    //    @StreamListener(Sink.INPUT)
//    public void receive(Message<Object> m,
//                        @Header(AmqpHeaders.CHANNEL) Channel channel
//            /*@Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag*/) {
//        log.info("Received:" + m.getPayload());
//    }
//


//    @StreamListener(Source.INPUT)
//    public void receive(Object payload) {
//        log.info("Received3:" + payload);
//    }


    @Bean

    @ConditionalOnExpression(
            "'${spring.cloud.stream.rabbit.bindings.TestInput.consumer.acknowledgeMode}'!='MANUAL'")
    public AutoReceiver autoReceiver() {
        return new AutoReceiver();
    }

    @Bean
    @ConditionalOnProperty(
            value = "spring.cloud.stream.rabbit.bindings.TestInput.consumer.acknowledgeMode",
            havingValue = "MANUAL")
//    @ConditionalOnExpression("'${spring.cloud.stream.rabbit.bindings.TestInput.consumer.acknowledgeMode}'=='MANUAL'")
    public MudlReceiver mudlReceiver() {
        return new MudlReceiver();
    }

}
