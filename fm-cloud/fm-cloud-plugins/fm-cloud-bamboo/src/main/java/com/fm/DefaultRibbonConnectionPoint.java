package com.fm;

import com.fm.cloud.bamboo.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class DefaultRibbonConnectionPoint  implements BambooRibbonConnectionPoint{

    private RequestVersionExtractor versionExtractor;
    private ApplicationContext ctx;
    private ThreadLocal<List<LoadBalanceRequestTrigger>> curRequestTriggers = new ThreadLocal();

    public DefaultRibbonConnectionPoint(RequestVersionExtractor versionExtractor) {
        this.versionExtractor = versionExtractor;
    }

    @Override
    public void executeConnectPoint(ConnectPointContext connectPointContext) {
        BambooRequest bambooRequest = connectPointContext.getBambooRequest();
        String requestVersion = versionExtractor.extractVersion(bambooRequest);
        BambooRequestContext.initRequestContext(bambooRequest, requestVersion);
        chooseRequestTrigger();
        executeBeforeReuqestTrigger();
    }

    @Override
    public void shutdownconnectPoint() {
        try {
            executeAfterReuqestTrigger();
        }finally {
            curRequestTriggers.remove();
            BambooRequestContext.shutdownRequestContext();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private void chooseRequestTrigger(){
        List<LoadBalanceRequestTrigger> requestTriggers = new ArrayList<>();
        ctx.getBeansOfType(LoadBalanceRequestTrigger.class).values().forEach(trigger ->{
            if(trigger.shouldExecute()){
                requestTriggers.add(trigger);
            }
        });
        if(!requestTriggers.isEmpty()){
            curRequestTriggers.set(requestTriggers);
        }
    }



    protected void executeBeforeReuqestTrigger(){
        List<LoadBalanceRequestTrigger> requestTriggers =  curRequestTriggers.get();
        if(requestTriggers!=null && !requestTriggers.isEmpty()){
            requestTriggers.forEach(LoadBalanceRequestTrigger::before);
        }
    }



    protected void executeAfterReuqestTrigger(){
        List<LoadBalanceRequestTrigger> requestTriggers =  curRequestTriggers.get();
        if(requestTriggers!=null && !requestTriggers.isEmpty()){
            requestTriggers.forEach(LoadBalanceRequestTrigger::after);
        }
    }
}
