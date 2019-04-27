package com.fm.mq.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Source {

//    String OUTPUT = "TestOutput";

    String INPUT = "TestInput";

    @Input(INPUT)
    SubscribableChannel input();

//    @Output(OUTPUT)
//    MessageChannel output();
}
