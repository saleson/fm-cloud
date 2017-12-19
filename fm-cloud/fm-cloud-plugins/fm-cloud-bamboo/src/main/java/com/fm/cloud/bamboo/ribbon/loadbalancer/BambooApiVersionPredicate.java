package com.fm.cloud.bamboo.ribbon.loadbalancer;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.List;
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
        } else if (com.netflix.zuul.context.RequestContext.getCurrentContext() != null) {
            RequestContext requestContext = com.netflix.zuul.context.RequestContext.getCurrentContext();
            List<String> versionList = requestContext.getRequestQueryParams().get("version");
            String apiVersion = versionList.size() > 0 ? versionList.get(0) : null;
            String serviceId = (String) requestContext.get("serviceId");
            if (!StringUtils.isEmpty(apiVersion) && !StringUtils.isEmpty(serviceId)) {
                Map<String, String> serverMetadata = ((BambooZoneAvoidanceRule) this.rule)
                        .getServerMetadata(serviceId, input.getServer());
                String versions = serverMetadata.get("versions");
                return matchVersion(versions, apiVersion);
            }
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
