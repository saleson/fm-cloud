package com.fm.gray;

import com.fm.gray.core.GrayServiceManager;
import com.fm.gray.server.GrayBunnyServerEvictor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PreDestroy;

public class GrayBunnyServerInitializingBean implements InitializingBean, ApplicationContextAware{
    private ApplicationContext cxt;

    @Override
    public void afterPropertiesSet() throws Exception {
        GrayBunnyServerContext.setGrayServiceManager(cxt.getBean(GrayServiceManager.class));
        GrayBunnyServerContext.setGrayBunnyServerEvictor(cxt.getBean(GrayBunnyServerEvictor.class));

        initToWork();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cxt = applicationContext;
    }

    private void initToWork(){
        GrayBunnyServerContext.getGrayServiceManager().openForWork();
    }


    @PreDestroy
    public void shutdown(){
        GrayBunnyServerContext.getGrayServiceManager().shutdown();
    }
}
