package com.fm.cloud.bamboo;

import org.springframework.context.ApplicationContextAware;

public interface BambooRibbonConnectionPoint {


    void executeConnectPoint(ConnectPointContext connectPointContext);


    void shutdownconnectPoint();


//    void executeBeforeReuqestTrigger();



//    void executeAfterReuqestTrigger();

}
