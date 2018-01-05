package com.fm.cloud.bamboo;

import com.fm.cloud.bamboo.ribbon.EurekaServerExtractor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BambooInitializingBean implements InitializingBean, ApplicationContextAware {


    private ApplicationContext ctx;

    @Override
    public void afterPropertiesSet() {
        BambooAppContext.setDefaultConnectionPoint(ctx.getBean(BambooRibbonConnectionPoint.class));
        BambooAppContext.setEurekaServerExtractor(ctx.getBean(EurekaServerExtractor.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
