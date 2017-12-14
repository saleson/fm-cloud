package com.fm.cloud.bamboo.ribbon;

import com.fm.cloud.bamboo.ribbon.client.loadbalancer.BambooLoadBalancerRequest;
import com.fm.cloud.bamboo.ribbon.loadbalancer.BambooLoadBalancerKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.netflix.ribbon.DefaultServerIntrospector;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonUtils;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by saleson on 2017/11/10.
 */
public class BambooRibbonLoadBalancerClient extends RibbonLoadBalancerClient {

    private SpringClientFactory clientFactory;

    public BambooRibbonLoadBalancerClient(SpringClientFactory clientFactory) {
        super(clientFactory);
        this.clientFactory = clientFactory;
    }

    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        if (request instanceof BambooLoadBalancerRequest) {
            BambooLoadBalancerRequest<T> _request = (BambooLoadBalancerRequest) request;
            if (!StringUtils.isEmpty(_request.getVersion())) {
                return versionExecute(serviceId, _request);
            }
        }
        return super.execute(serviceId, request);
    }

    private <T> T versionExecute(String serviceId, BambooLoadBalancerRequest<T> request) throws IOException {
        return versionExecute(serviceId, request, request.getVersion());
    }

    private <T> T versionExecute(String serviceId, LoadBalancerRequest<T> request, String apiVersion) throws IOException {
        ILoadBalancer loadBalancer = getLoadBalancer(serviceId);
        Server server = getServer(serviceId, loadBalancer, apiVersion);
        if (server == null) {
            throw new IllegalStateException("No instances available for " + serviceId);
        }
        RibbonServer ribbonServer = new RibbonServer(serviceId, server, isSecure(server,
                serviceId), serverIntrospector(serviceId).getMetadata(server));

        return execute(serviceId, ribbonServer, request);
    }

    protected Server getServer(String serviceId, ILoadBalancer loadBalancer, String apiVersion) {
        if (loadBalancer == null) {
            return null;
        }
        return loadBalancer.chooseServer(
                BambooLoadBalancerKey.builder().apiVersion(apiVersion)
                        .serviceId(serviceId).build());
    }

    private boolean isSecure(Server server, String serviceId) {
        IClientConfig config = this.clientFactory.getClientConfig(serviceId);
        ServerIntrospector serverIntrospector = serverIntrospector(serviceId);
        return RibbonUtils.isSecure(config, serverIntrospector, server);
    }

    public ServerIntrospector serverIntrospector(String serviceId) {
        ServerIntrospector serverIntrospector = this.clientFactory.getInstance(serviceId,
                ServerIntrospector.class);
        if (serverIntrospector == null) {
            serverIntrospector = new DefaultServerIntrospector();
        }
        return serverIntrospector;
    }
}
