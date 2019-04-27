package com.fm.mq.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Source {

    String OUTPUT = "TestOutput";

//    String INPUT = "TestInput";

//    @Input(INPUT)
//    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
