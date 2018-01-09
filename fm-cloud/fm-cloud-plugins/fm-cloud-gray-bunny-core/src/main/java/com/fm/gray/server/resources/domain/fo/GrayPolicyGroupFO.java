package com.fm.gray.server.resources.domain.fo;

import com.fm.gray.core.GrayPolicy;
import com.fm.gray.core.GrayPolicyGroup;

import java.util.List;

public class GrayPolicyGroupFO {
    private String instanceId;
    private String policyGroupId;
    private String alias;
    private List<GrayPolicy> policies;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getPolicyGroupId() {
        return policyGroupId;
    }

    public void setPolicyGroupId(String policyGroupId) {
        this.policyGroupId = policyGroupId;
    }

    public List<GrayPolicy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<GrayPolicy> policies) {
        this.policies = policies;
    }


    public GrayPolicyGroup toGrayPolicyGroup(){
        GrayPolicyGroup policyGroup = new GrayPolicyGroup();
        policyGroup.setAlias(this.getAlias());
        policyGroup.setList(this.getPolicies());
        policyGroup.setPolicyGroupId(this.getPolicyGroupId());
        return policyGroup;
    }
}
