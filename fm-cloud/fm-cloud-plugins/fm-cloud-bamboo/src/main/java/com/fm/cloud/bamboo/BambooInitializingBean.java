package com.fm.cloud.bamboo;

import com.fm.cloud.bamboo.ribbon.EurekaServerExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BambooInitializingBean implements InitializingBean, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(BambooInitializingBean.class);


    private ApplicationContext ctx;

    @Override
    public void afterPropertiesSet() {
        BambooAppContext.setDefaultConnectionPoint(ctx.getBean(BambooRibbonConnectionPoint.class));
        BambooAppContext.setEurekaServerExtractor(ctx.getBean(EurekaServerExtractor.class));
        setLocalIp();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private void setLocalIp(){
        String ip = null;
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
            ip = inet.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("[IpHelper-getIpAddr] IpHelper error.", e);
        }
        BambooAppContext.setLocalIp(ip);
    }
}
