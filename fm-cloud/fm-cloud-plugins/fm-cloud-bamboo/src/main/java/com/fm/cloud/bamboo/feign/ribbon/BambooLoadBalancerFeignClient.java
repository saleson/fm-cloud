package com.fm.cloud.bamboo.feign.ribbon;

import feign.Client;
import org.springframework.cloud.netflix.feign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

/**
 * Created by saleson on 2017/11/13.
 */
@Deprecated
public class BambooLoadBalancerFeignClient extends LoadBalancerFeignClient {
    public BambooLoadBalancerFeignClient(Client delegate, CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory) {
        super(delegate, lbClientFactory, clientFactory);
    }


}
