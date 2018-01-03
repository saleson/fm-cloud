package com.fm.cloud.bamboo;

import com.fm.cloud.bamboo.feign.BambooFeighConfiguration;
import com.fm.cloud.bamboo.ribbon.BambooClientHttpRequestIntercptor;
import com.fm.cloud.bamboo.ribbon.BambooRibbonLoadBalancerClient;
import com.fm.cloud.bamboo.ribbon.EurekaServerExtractor;
import com.fm.cloud.bamboo.ribbon.loadbalancer.BambooZoneAvoidanceRule;
import com.fm.cloud.bamboo.zuul.BambooZuulConfiguration;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClientSpecification;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by saleson on 2017/11/9.
 */
@Configuration
@EnableConfigurationProperties
@AutoConfigureBefore({BambooFeighConfiguration.class, BambooZuulConfiguration.class})
//@RibbonClients(defaultConfiguration = {BambooExtConfigration.class})
public class BambooConfiguration {

//    @Autowired
//    private PropertiesFactory propertiesFactory;
//
//    @Autowired(required = false)
//    private List<RibbonClientSpecification> configurations = new ArrayList<RibbonClientSpecification>();
//
//    @Autowired(required = false)
//    private List<LoadBalancerRequestTransformer> transformers = Collections.emptyList();
//
//
//    //    @Value("${ribbon.client.name}")
////    private String name = "client";
    @Autowired(required = false)
    private IClientConfig config;

    @Autowired
    private SpringClientFactory springClientFactory;
//
//
//    @Bean
//    @Primary
//    public SpringClientFactory springClientFactory() {
//        SpringClientFactory factory = new SpringClientFactory();
//        factory.setConfigurations(this.configurations);
//        return factory;
//    }
//
//    @Bean
////    @ConditionalOnMissingBean(LoadBalancerClient.class)
//    public BambooRibbonLoadBalancerClient loadBalancerClient(SpringClientFactory springClientFactory) {
//        return new BambooRibbonLoadBalancerClient(springClientFactory);
//    }


//    @Bean
////    @ConditionalOnMissingBean
//    public IRule ribbonRule(BambooRibbonLoadBalancerClient loadBalancerClient) {
////        if (this.propertiesFactory.isSet(IRule.class, name)) {
////            return this.propertiesFactory.get(IRule.class, config, name);
////        }
//        BambooZoneAvoidanceRule rule = new BambooZoneAvoidanceRule(loadBalancerClient);
//        rule.initWithNiwsConfig(config);
//        return rule;
//    }


//    @Bean
////    @ConditionalOnMissingBean
//    public LoadBalancerRequestFactory loadBalancerRequestFactory(
//            LoadBalancerClient loadBalancerClient) {
//        return new BambooLoadBalancerRequestFactory(loadBalancerClient, transformers);
//    }


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BambooClientHttpRequestIntercptor());
        return restTemplate;
    }

    @Bean
    public EurekaServerExtractor eurekaServerExtractor(){
        return new EurekaServerExtractor(springClientFactory);
    }


    @Bean
    public IRule ribbonRule(EurekaServerExtractor eurekaServerExtractor) {
        BambooZoneAvoidanceRule rule = new BambooZoneAvoidanceRule(eurekaServerExtractor);
        rule.initWithNiwsConfig(config);
        return rule;
    }

}
