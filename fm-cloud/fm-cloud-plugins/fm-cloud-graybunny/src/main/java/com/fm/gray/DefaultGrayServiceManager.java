package com.fm.gray;

import com.fm.gray.core.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultGrayServiceManager implements GrayServiceManager {


    private Map<String, GrayService> grayServiceMap = new ConcurrentHashMap<>();
    private Lock lock = new ReentrantLock();


    @Override
    public void addGrayInstance(GrayInstance instance) {
        GrayService grayService = grayServiceMap.get(instance.getServiceId());
        lock.lock();
        try {
            if (grayService == null) {
                grayService = new GrayService();
                grayService.setServiceId(instance.getServiceId());
                grayServiceMap.put(instance.getServiceId(), grayService);
            }
            if (!grayService.contains(instance.getInstanceId())) {
                grayService.addGrayInstance(instance);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteGrayInstance(String serviceId, String instanceId) {
        GrayService grayService = grayServiceMap.get(serviceId);
        if (grayService == null) {
            return;
        }
        lock.lock();
        try {
            if (grayService.removeGrayInstance(instanceId) != null && grayService.getGrayInstances().isEmpty()) {
                grayServiceMap.remove(serviceId);
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void addGrayPolicy(String serviceId, String instanceId, String policyGroupId, GrayPolicy policy) {
        GrayInstance grayInstance= getGrayInstane(serviceId, instanceId);
        if(grayInstance!=null){
            grayInstance.addGrayPolicy(policyGroupId, policy);
        }
    }

    @Override
    public void deleteGrayPolicy(String serviceId, String instanceId, String policyGroupId, String policyId) {
        GrayInstance grayInstance= getGrayInstane(serviceId, instanceId);
        if(grayInstance!=null){
            grayInstance.removeGrayPolicy(policyGroupId, policyId);
        }
    }

    @Override
    public void addGrayPolicyGroup(String serviceId, String instanceId, GrayPolicyGroup policyGroup) {
        GrayInstance grayInstance= getGrayInstane(serviceId, instanceId);
        if(grayInstance!=null){
            grayInstance.addGrayPolicyGroup(policyGroup);
        }
    }

    @Override
    public void deleteGrayPolicyGroup(String serviceId, String instanceId, String policyGroupId) {
        GrayInstance grayInstance= getGrayInstane(serviceId, instanceId);
        if(grayInstance!=null){
            grayInstance.removeGrayPolicyGroup(policyGroupId);
        }
    }


    @Override
    public Collection<GrayService> allGrayService() {
        return Collections.unmodifiableCollection(grayServiceMap.values());
    }

    @Override
    public GrayService getGrayService(String serviceId){
        return grayServiceMap.get(serviceId);
    }



    @Override
    public GrayInstance getGrayInstane(String serviceId, String instanceId){
        GrayService grayService = getGrayService(serviceId);
        if(grayService!=null){
            return grayService.getGrayInstance(instanceId);
        }
        return null;
    }
}
