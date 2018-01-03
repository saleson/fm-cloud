package com.fm.cloud.bamboo.feign;

import com.netflix.loadbalancer.ILoadBalancer;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created by saleson on 2017/11/9.
 */
@ConditionalOnClass({ILoadBalancer.class, Feign.class})
@Configuration
@EnableFeignClients(defaultConfiguration = {BambooFeighClientsConfiguration.class})
public class BambooFeighConfiguration {


//    @Bean
//    @Primary
////    @ConditionalOnMissingClass("org.springframework.retry.support.RetryTemplate")
//    public BambooCachingSpringLoadBalancerFactory bambooCachingLBClientFactory(
//            SpringClientFactory factory) {
//        return new BambooCachingSpringLoadBalancerFactory(factory);
//    }

//    @Bean
//    @Primary
//    @ConditionalOnClass(name = "org.springframework.retry.support.RetryTemplate")
//    public BambooCachingSpringLoadBalancerFactory retryabeCachingLBClientFactory(
//            SpringClientFactory factory, LoadBalancedRetryPolicyFactory retryPolicyFactory) {
//        return new BambooCachingSpringLoadBalancerFactory(factory, retryPolicyFactory, true);
//    }

//    @Bean
//    @Primary
//    public Client feignClient(BambooCachingSpringLoadBalancerFactory cachingFactory,
//                              SpringClientFactory clientFactory) {
//        return new LoadBalancerFeignClient(new Client.Default(null, null),
//                cachingFactory, clientFactory);
//    }
}
