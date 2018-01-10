package com.fm.gray;

import com.fm.gray.client.InstanceLocalInfo;
import com.fm.gray.core.GrayManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PreDestroy;

public class GrayBunnyInitializingBean implements InitializingBean, ApplicationContextAware {
    private ApplicationContext cxt;

    @Override
    public void afterPropertiesSet() throws Exception {
        GrayBunnyAppContext.setGrayManager(cxt.getBean(GrayManager.class));
        GrayBunnyAppContext.setInstanceLocalInfo(cxt.getBean(InstanceLocalInfo.class));

        startForWork();

//        registrShutdownFunc();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.cxt = applicationContext;
    }

//    private void registrShutdownFunc(){
//        Runtime.getRuntime().addShutdownHook(new Thread(()->{
//            shutdown();
//        }));
//    }

    @PreDestroy
    public void shutdown(){
        GrayBunnyAppContext.getGrayManager().serviceDownline();
    }

    private void startForWork(){
        GrayBunnyAppContext.getGrayManager().openForWork();
    }
}