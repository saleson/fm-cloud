package com.fm.gray.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GrayService {
    private String serviceId;
    private List<GrayInstance> grayInstances = new ArrayList<>();




    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public List<GrayInstance> getGrayInstances() {
        return grayInstances;
    }

    public void setGrayInstances(List<GrayInstance> grayInstances) {
        this.grayInstances = grayInstances;
    }


    public boolean contains(String instanceId){
        for (GrayInstance grayInstance : grayInstances) {
            if(grayInstance.getInstanceId().equals(instanceId)){
                return true;
            }
        }
        return false;
    }

    public void addGrayInstance(GrayInstance grayInstance){
        grayInstances.add(grayInstance);
    }

    public GrayInstance removeGrayInstance(String instanceId) {
        Iterator<GrayInstance> iter = grayInstances.iterator();
        while(iter.hasNext()){
            GrayInstance instance = iter.next();
            if(instance.getInstanceId().equals(instanceId)){
                iter.remove();
                return instance;
            }
        }
        return null;
    }



    public GrayInstance getGrayInstance(String instanceId){
        Iterator<GrayInstance> iter = grayInstances.iterator();
        while(iter.hasNext()){
            GrayInstance instance = iter.next();
            if(instance.getInstanceId().equals(instanceId)){
                return instance;
            }
        }
        return null;
    }

    public  int countGrayPolicy(){
        int count = 0;
        for (GrayInstance grayInstance : grayInstances) {
            for (GrayPolicyGroup policyGroup : grayInstance.getGrayPolicyGroups()) {
                count+=policyGroup.getList().size();
            }
        }
        return count;
    }

    public boolean isOpenGray(){
        return getGrayInstances()!=null
                && !getGrayInstances().isEmpty()
                && hasGrayPolicy();
    }

    public boolean hasGrayPolicy(){
        for (GrayInstance grayInstance : getGrayInstances()) {
                if(grayInstance.hasGrayPolicy()){
                    return true;
                }
        }
        return false;
    }
}
