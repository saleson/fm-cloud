package com.fm.cloud.bamboo.feign.ribbon;

import com.fm.cloud.bamboo.ApiVersionHelper;
import com.fm.cloud.bamboo.ribbon.loadbalancer.BambooLoadBalancerKey;
import com.netflix.client.ClientException;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;

import java.net.URI;

/**
 * Created by saleson on 2017/11/13.
 */
public class BambooFeignLoadBalancer extends FeignLoadBalancer {


    public BambooFeignLoadBalancer(ILoadBalancer lb, IClientConfig clientConfig, ServerIntrospector serverIntrospector) {
        super(lb, clientConfig, serverIntrospector);
    }


    @Override
    public Server getServerFromLoadBalancer(URI original, Object loadBalancerKey) throws ClientException {
        if (loadBalancerKey == null) {
            loadBalancerKey = BambooLoadBalancerKey.builder().apiVersion(ApiVersionHelper.getApiVersion(original))
                    .serviceId(getClientName()).build();
        }
        return super.getServerFromLoadBalancer(original, loadBalancerKey);
    }
}
