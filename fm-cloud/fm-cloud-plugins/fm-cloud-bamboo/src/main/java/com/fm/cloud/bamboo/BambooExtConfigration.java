package com.fm.cloud.bamboo;

import com.fm.cloud.bamboo.ribbon.LoadBalancerAwareClientBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by saleson on 2017/12/21.
 */
@Configuration
public class BambooExtConfigration {
    @Bean
    public LoadBalancerAwareClientBeanPostProcessor loadBalancerAwareClientBeanPostProcessor() {
        return new LoadBalancerAwareClientBeanPostProcessor();
    }
}
