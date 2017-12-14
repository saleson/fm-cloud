package com.fm.cloud.bamboo.ribbon.loadbalancer;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by saleson on 2017/11/10.
 */
public class BambooApiVersionPredicate extends AbstractServerPredicate {

    public BambooApiVersionPredicate(BambooZoneAvoidanceRule rule) {
        super(rule);
    }

    @Override
    public boolean apply(PredicateKey input) {
        if (input.getLoadBalancerKey() != null && input.getLoadBalancerKey() instanceof BambooLoadBalancerKey) {
            BambooLoadBalancerKey loadBalancerKey = (BambooLoadBalancerKey) input.getLoadBalancerKey();
            Map<String, String> serverMetadata = ((BambooZoneAvoidanceRule) this.rule)
                    .getServerMetadata(loadBalancerKey.getServiceId(), input.getServer());
            String versions = serverMetadata.get("versions");
            return matchVersion(versions, loadBalancerKey.getApiVersion());

        }
        return true;
    }

    private boolean matchVersion(String serverVersions, String apiVersion) {
        if (StringUtils.isEmpty(serverVersions)) {
            return false;
        }
        String[] versions = StringUtils.split(serverVersions, ",");
        return ArrayUtils.contains(versions, apiVersion);
    }
}
