package com.fm.gray;

import com.fm.gray.core.*;

import java.util.Collection;

public class RedisGrayServiceManager implements GrayServiceManager {


    @Override
    public void addGrayInstance(GrayInstance instance) {

    }

    @Override
    public void deleteGrayInstance(String serviceId, String instanceId) {

    }

    @Override
    public void addGrayPolicy(String serviceId, String instanceId, String policyGroupId, GrayPolicy policy) {

    }

    @Override
    public void deleteGrayPolicy(String serviceId, String instanceId, String policyGroupId, String policyId) {

    }

    @Override
    public void addGrayPolicyGroup(String serviceId, String instanceId, GrayPolicyGroup policyGroup) {

    }

    @Override
    public void deleteGrayPolicyGroup(String serviceId, String instanceId, String policyGroupId) {

    }

    @Override
    public Collection<GrayService> allGrayService() {
        return null;
    }

    @Override
    public GrayService getGrayService(String serviceId) {
        return null;
    }

    @Override
    public GrayInstance getGrayInstane(String serviceId, String instanceId) {
        return null;
    }
}
