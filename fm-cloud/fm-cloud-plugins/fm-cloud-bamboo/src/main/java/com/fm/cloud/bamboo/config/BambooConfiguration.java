package com.fm.cloud.bamboo.config;

import com.fm.cloud.bamboo.*;
import com.fm.cloud.bamboo.feign.BambooFeighConfiguration;
import com.fm.cloud.bamboo.ribbon.BambooClientHttpRequestIntercptor;
import com.fm.cloud.bamboo.ribbon.EurekaServerExtractor;
import com.fm.cloud.bamboo.ribbon.loadbalancer.BambooZoneAvoidanceRule;
import com.fm.cloud.bamboo.zuul.BambooZuulConfiguration;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by saleson on 2017/11/9.
 */
@Configuration
@EnableConfigurationProperties
@AutoConfigureBefore({BambooFeighConfiguration.class, BambooZuulConfiguration.class})
@Import(BambooWebConfiguration.class)
//@RibbonClients(defaultConfiguration = {BambooExtConfigration.class})
public class BambooConfiguration {


    public static class UnUseBambooIRule{

    }


    @Autowired(required = false)
    private IClientConfig config;

    @Autowired
    private SpringClientFactory springClientFactory;



    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BambooClientHttpRequestIntercptor());
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public EurekaServerExtractor eurekaServerExtractor(){
        return new EurekaServerExtractor(springClientFactory);
    }


    @Bean
    @ConditionalOnMissingBean(value = {BambooConfiguration.UnUseBambooIRule.class})
    public IRule ribbonRule() {
        BambooZoneAvoidanceRule rule = new BambooZoneAvoidanceRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestVersionExtractor requestVersionExtractor(){
        return new RequestVersionExtractor.Default();
    }


    @Bean
    @ConditionalOnMissingBean
    public BambooRibbonConnectionPoint bambooRibbonConnectionPoint(
            RequestVersionExtractor requestVersionExtractor,
            @Autowired(required = false) List<LoadBalanceRequestTrigger> requestTriggerList){
        if(requestTriggerList!=null){
            requestTriggerList = Collections.EMPTY_LIST;
        }
        return new DefaultRibbonConnectionPoint(requestVersionExtractor, requestTriggerList);
    }

    @Bean
    public BambooInitializingBean bambooInitializingBean(){
        return new BambooInitializingBean();
    }

}
