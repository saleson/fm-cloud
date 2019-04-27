package com.fm.seceiver;

import com.fm.mq.component.Source;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.concurrent.atomic.LongAdder;

@EnableBinding({Source.class})
public class MudlReceiver {

    private static final Logger log = LoggerFactory.getLogger(MudlReceiver.class);

    private LongAdder count = new LongAdder();

    @StreamListener(Source.INPUT)
    public void receive5(@Payload Object payload,
                         @Header(AmqpHeaders.CHANNEL) Channel channel,
                         @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        count.increment();
        log.info("/n {} Received5: {}", count.longValue(), payload);
//        channel.basicReject(deliveryTag, true);
        if ((count.longValue() % 2) == 1) {
            channel.basicAck(deliveryTag, false);
        } else {
            log.info("拒绝发送" + payload);
            channel.basicReject(deliveryTag, true);
        }

    }
}
