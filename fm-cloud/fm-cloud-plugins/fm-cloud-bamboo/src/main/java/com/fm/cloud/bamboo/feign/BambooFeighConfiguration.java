package com.fm.cloud.bamboo.feign;

import com.fm.cloud.bamboo.feign.ribbon.BambooCachingSpringLoadBalancerFactory;
import com.netflix.loadbalancer.ILoadBalancer;
import feign.Client;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by saleson on 2017/11/9.
 */
@ConditionalOnClass({ILoadBalancer.class, Feign.class})
@Configuration
@EnableConfigurationProperties
public class BambooFeighConfiguration {


    @Bean
    @Primary
//    @ConditionalOnMissingClass("org.springframework.retry.support.RetryTemplate")
    public BambooCachingSpringLoadBalancerFactory bambooCachingLBClientFactory(
            SpringClientFactory factory) {
        return new BambooCachingSpringLoadBalancerFactory(factory);
    }

//    @Bean
//    @Primary
//    @ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
//    public BambooCachingSpringLoadBalancerFactory retryabeCachingLBClientFactory(
//            SpringClientFactory factory, LoadBalancedRetryPolicyFactory retryPolicyFactory) {
//        return new BambooCachingSpringLoadBalancerFactory(factory, retryPolicyFactory, true);
//    }

    @Bean
    @Primary
    public Client feignClient(BambooCachingSpringLoadBalancerFactory cachingFactory,
                              SpringClientFactory clientFactory) {
        return new LoadBalancerFeignClient(new Client.Default(null, null),
                cachingFactory, clientFactory);
    }
}
