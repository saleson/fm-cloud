package com.fm.cloud.bamboo;

public interface LoadBalanceRequestTrigger {


    boolean shouldExecute();

    void before();

    void after();
}
