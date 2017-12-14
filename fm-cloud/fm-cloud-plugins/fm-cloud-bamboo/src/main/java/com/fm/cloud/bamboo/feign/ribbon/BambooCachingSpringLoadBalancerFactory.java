package com.fm.cloud.bamboo.feign.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicyFactory;
import org.springframework.cloud.netflix.feign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer;
import org.springframework.cloud.netflix.feign.ribbon.RetryableFeignLoadBalancer;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryPolicyFactory;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;

/**
 * Created by saleson on 2017/11/13.
 */
public class BambooCachingSpringLoadBalancerFactory extends CachingSpringLoadBalancerFactory {
    public BambooCachingSpringLoadBalancerFactory(SpringClientFactory factory) {
        super(factory);
        this.factory = factory;
        this.loadBalancedRetryPolicyFactory = new RibbonLoadBalancedRetryPolicyFactory(factory);
    }

    public BambooCachingSpringLoadBalancerFactory(SpringClientFactory factory, LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory) {
        super(factory, loadBalancedRetryPolicyFactory);
        this.factory = factory;
        this.loadBalancedRetryPolicyFactory = loadBalancedRetryPolicyFactory;
    }

    public BambooCachingSpringLoadBalancerFactory(SpringClientFactory factory, LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory, boolean enableRetry) {
        super(factory, loadBalancedRetryPolicyFactory, enableRetry);
        this.factory = factory;
        this.loadBalancedRetryPolicyFactory = loadBalancedRetryPolicyFactory;
        this.enableRetry = enableRetry;
    }


    protected SpringClientFactory factory;
    protected LoadBalancedRetryPolicyFactory loadBalancedRetryPolicyFactory;
    protected boolean enableRetry = false;

    protected volatile Map<String, FeignLoadBalancer> cache = new ConcurrentReferenceHashMap<>();


    @Override
    public FeignLoadBalancer create(String clientName) {
        if (this.cache.containsKey(clientName)) {
            return this.cache.get(clientName);
        }
        IClientConfig config = this.factory.getClientConfig(clientName);
        ILoadBalancer lb = this.factory.getLoadBalancer(clientName);
        ServerIntrospector serverIntrospector = this.factory.getInstance(clientName, ServerIntrospector.class);
        FeignLoadBalancer client = enableRetry ? new RetryableFeignLoadBalancer(lb, config, serverIntrospector,
                loadBalancedRetryPolicyFactory) : new BambooFeignLoadBalancer(lb, config, serverIntrospector);
        this.cache.put(clientName, client);
        return client;
    }


}
