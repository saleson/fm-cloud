package com.fm.seceiver;

import com.fm.mq.component.Source;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;

@EnableBinding({Source.class})
public class MudlReceiver {

    private static final Logger log = LoggerFactory.getLogger(MudlReceiver.class);

    //    @StreamListener(Source.INPUT)
    public void receive5(@Payload Object payload,
                         @Header(AmqpHeaders.CHANNEL) Channel channel,
                         @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        log.info("Received5:" + payload);
//        channel.basicReject(deliveryTag, true);
        channel.basicAck(deliveryTag, false);
    }
}
