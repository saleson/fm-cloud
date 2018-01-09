package com.fm.gray;

import com.fm.gray.core.GrayManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class GrayBunnyInitializingBean implements InitializingBean, ApplicationContextAware {
    private ApplicationContext cxt;

    @Override
    public void afterPropertiesSet() throws Exception {
        GrayBunnyAppContext.setGrayManager(cxt.getBean(GrayManager.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cxt = applicationContext;
    }
}