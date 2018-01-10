package com.fm.gray.core;

import java.util.Collection;
import java.util.List;

public interface GrayServiceManager {

    void addGrayInstance(GrayInstance instance);

    void deleteGrayInstance(String serviceId, String instanceId);

    void addGrayPolicy(String serviceId, String instanceId, String policyGroupId, GrayPolicy policy);

    void deleteGrayPolicy(String serviceId, String instanceId, String policyGroupId, String policyId);

    void addGrayPolicyGroup(String serviceId, String instanceId, GrayPolicyGroup policyGroup);

    void deleteGrayPolicyGroup(String serviceId, String instanceId, String policyGroupId);

    Collection<GrayService> allGrayService();

    GrayService getGrayService(String serviceId);

    GrayInstance getGrayInstane(String serviceId, String instanceId);

    /**
     * 打开检查
     */
    void openForInspection();


    void shutdown();
}
