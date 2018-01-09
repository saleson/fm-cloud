package com.fm.gray;

import com.fm.gray.core.GrayServiceManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class GrayBunnyServerInitializingBean implements InitializingBean, ApplicationContextAware{
    private ApplicationContext cxt;

    @Override
    public void afterPropertiesSet() throws Exception {
        GrayBunnyServerContext.setGrayServiceManager(cxt.getBean(GrayServiceManager.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cxt = applicationContext;
    }
}
