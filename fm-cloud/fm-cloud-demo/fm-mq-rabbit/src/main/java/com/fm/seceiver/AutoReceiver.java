package com.fm.seceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoReceiver {

    private static final Logger log = LoggerFactory.getLogger(AutoReceiver.class);

    //    @StreamListener(Source.INPUT)
    public void receive(Object payload) {
        log.info("Received3:" + payload);
    }
}

