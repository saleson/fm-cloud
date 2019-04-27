package com.fm.cloud.bamboo.ribbon.client.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;

/**
 * Created by saleson on 2017/11/10.
 */
@Deprecated
public class BambooLoadBalancerRequest<T> implements LoadBalancerRequest<T> {

    private LoadBalancerRequest<T> request;
    private String version;

    public BambooLoadBalancerRequest(LoadBalancerRequest<T> request) {
        this.request = request;
    }

    public BambooLoadBalancerRequest(LoadBalancerRequest<T> request, String version) {
        this.request = request;
        this.version = version;
    }

    public LoadBalancerRequest<T> getRequest() {
        return request;
    }

    public void setRequest(LoadBalancerRequest<T> request) {
        this.request = request;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public T apply(ServiceInstance instance) throws Exception {
        return request.apply(instance);
    }
}
