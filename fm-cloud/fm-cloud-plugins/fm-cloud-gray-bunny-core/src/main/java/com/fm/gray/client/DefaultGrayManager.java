package com.fm.gray.client;

import com.fm.gray.core.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultGrayManager implements GrayManager {

    protected GrayDecisionFactory decisionFactory;
    protected InformationClient client;

    public DefaultGrayManager(InformationClient client, GrayDecisionFactory decisionFactory) {
        this.decisionFactory = decisionFactory;
        this.client = client;
    }


    @Override
    public boolean isOpen(String serviceId) {
        GrayService grayService = grayService(serviceId);
        return grayService!=null
                && grayService.isOpenGray();
    }

    @Override
    public List<GrayService> listGrayService() {
        return client.listGrayService();
    }

    @Override
    public GrayService grayService(String serviceId) {
        return client.grayService(serviceId);
    }

    @Override
    public GrayInstance grayInstance(String serviceId, String instanceId) {
        return client.grayInstance(serviceId, instanceId);
    }

    @Override
    public List<GrayDecision> grayDecision(GrayInstance instance) {
        return grayDecision(instance.getServiceId(), instance.getInstanceId());
    }

    @Override
    public List<GrayDecision> grayDecision(String serviceId, String instanceId) {
        GrayInstance grayInstance = client.grayInstance(serviceId, instanceId);
        if(grayInstance==null || grayInstance.getGrayPolicyGroups()==null || grayInstance.getGrayPolicyGroups().isEmpty()){
            return Collections.emptyList();
        }
        List<GrayPolicyGroup> policyGroups = grayInstance.getGrayPolicyGroups();
        List<GrayDecision> decisions = new ArrayList<>(policyGroups.size());
        for (GrayPolicyGroup policyGroup : policyGroups){
            GrayDecision grayDecision = toGrayDecision(policyGroup);
            if(grayDecision!=GrayDecision.refuse()){
                decisions.add(grayDecision);
            }
        }
        return decisions;
    }


    private GrayDecision toGrayDecision(GrayPolicyGroup policyGroup){
        List<GrayPolicy> policies = policyGroup.getList();
        if(policies==null || policies.isEmpty()){
            return GrayDecision.refuse();
        }
        MultiGrayDecision decision = new MultiGrayDecision(GrayDecision.allow());
        policies.forEach(policy -> decision.and(decisionFactory.getDecision(policy)));
        return decision;
    }
}
