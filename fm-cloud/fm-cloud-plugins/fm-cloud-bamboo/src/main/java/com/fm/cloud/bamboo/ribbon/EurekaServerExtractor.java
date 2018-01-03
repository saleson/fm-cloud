package com.fm.cloud.bamboo.ribbon;

import com.netflix.loadbalancer.Server;
import org.springframework.cloud.netflix.ribbon.DefaultServerIntrospector;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

import java.util.Map;

public class EurekaServerExtractor {


    private SpringClientFactory clientFactory;

    public EurekaServerExtractor(SpringClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public ServerIntrospector serverIntrospector(String serviceId) {
        ServerIntrospector serverIntrospector = this.clientFactory.getInstance(serviceId,
                ServerIntrospector.class);
        if (serverIntrospector == null) {
            serverIntrospector = new DefaultServerIntrospector();
        }
        return serverIntrospector;
    }

    public Map<String, String> getServerMetadata(String serviceId, Server server) {
        return serverIntrospector(serviceId).getMetadata(server);
    }

}
