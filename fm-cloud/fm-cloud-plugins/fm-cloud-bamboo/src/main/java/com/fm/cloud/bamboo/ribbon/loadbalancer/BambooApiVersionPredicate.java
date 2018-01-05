package com.fm.cloud.bamboo.ribbon.loadbalancer;

import com.fm.cloud.bamboo.BambooRequest;
import com.fm.cloud.bamboo.ribbon.BambooRequestContext;
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
        BambooLoadBalancerKey loadBalancerKey = getBambooLoadBalancerKey(input);
        if (loadBalancerKey != null && !StringUtils.isEmpty(loadBalancerKey.getApiVersion())) {
            Map<String, String> serverMetadata = ((BambooZoneAvoidanceRule) this.rule)
                    .getServerMetadata(loadBalancerKey.getServiceId(), input.getServer());
            String versions = serverMetadata.get("versions");
            return matchVersion(versions, loadBalancerKey.getApiVersion());
        }
        return true;
    }

    private BambooLoadBalancerKey getBambooLoadBalancerKey(PredicateKey input) {
        /*if (input.getLoadBalancerKey() != null && input.getLoadBalancerKey() instanceof BambooLoadBalancerKey) {
            return (BambooLoadBalancerKey) input.getLoadBalancerKey();
        } else if (BambooRequestContext.instance() != null) {
            return BambooRequestContext.instance().getLoadBalancerKey();
        } else */if(com.fm.cloud.bamboo.BambooRequestContext.currentRequestCentxt()!=null){
            com.fm.cloud.bamboo.BambooRequestContext bambooRequestContext = com.fm.cloud.bamboo.BambooRequestContext.currentRequestCentxt();
            String apiVersion = bambooRequestContext.getApiVersion();
            if(!StringUtils.isEmpty(apiVersion)){
                return BambooLoadBalancerKey.builder().apiVersion(apiVersion)
                        .serviceId(bambooRequestContext.getServiceId()).build();
            }
        }
        return null;
    }

    /**
     * 匹配api version
     * @param serverVersions
     * @param apiVersion
     * @return
     */
    private boolean matchVersion(String serverVersions, String apiVersion) {
        if (StringUtils.isEmpty(serverVersions)) {
            return false;
        }
        String[] versions = StringUtils.split(serverVersions, ",");
        return ArrayUtils.contains(versions, apiVersion);
    }
}
