package com.fm.cloud.bamboo.ribbon.loadbalancer;

import com.fm.cloud.bamboo.BambooAppContext;
import com.fm.cloud.bamboo.BambooRequestContext;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by saleson on 2017/11/9.
 */
public class BambooZoneAvoidanceRule extends ZoneAvoidanceRule {

    protected CompositePredicate bambooCompositePredicate;

    public BambooZoneAvoidanceRule() {
        super();
        BambooApiVersionPredicate apiVersionPredicate = new BambooApiVersionPredicate(this);
        bambooCompositePredicate = CompositePredicate.withPredicates(super.getPredicate(),
                apiVersionPredicate).build();
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return bambooCompositePredicate;
    }


    public Map<String, String> getServerMetadata(String serviceId, Server server) {
        return BambooAppContext.getEurekaServerExtractor().getServerMetadata(serviceId, server);
    }
}
