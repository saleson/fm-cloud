package com.fm.cloud.bamboo.ribbon.client.loadbalancer;

import com.fm.cloud.bamboo.ApiVersionHelper;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.util.List;

/**
 * Created by saleson on 2017/11/10.
 */
@Deprecated
public class BambooLoadBalancerRequestFactory extends LoadBalancerRequestFactory {
    public BambooLoadBalancerRequestFactory(LoadBalancerClient loadBalancer, List<LoadBalancerRequestTransformer> transformers) {
        super(loadBalancer, transformers);
    }

    public BambooLoadBalancerRequestFactory(LoadBalancerClient loadBalancer) {
        super(loadBalancer);
    }


    @Override
    public LoadBalancerRequest<ClientHttpResponse> createRequest(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        String version = getApiVersion(request);
        return new BambooLoadBalancerRequest<ClientHttpResponse>(
                super.createRequest(request, body, execution), version);
    }

    private String getApiVersion(HttpRequest request) {
        return ApiVersionHelper.getApiVersion(request.getURI());
    }


}

