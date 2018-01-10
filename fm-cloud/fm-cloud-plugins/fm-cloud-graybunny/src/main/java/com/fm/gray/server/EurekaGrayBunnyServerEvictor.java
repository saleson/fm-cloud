package com.fm.gray.server;

import com.fm.gray.core.GrayInstance;
import com.fm.gray.core.GrayService;
import com.fm.gray.core.GrayServiceManager;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import java.util.Collection;

public class EurekaGrayBunnyServerEvictor implements GrayBunnyServerEvictor{

    private EurekaClient eurekaClient;


    public EurekaGrayBunnyServerEvictor(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Override
    public void evict(GrayServiceManager serviceManager) {
        Collection<GrayService> grayServices =  serviceManager.allGrayService();
        grayServices.forEach(grayService -> {
            grayService.getGrayInstances().forEach(grayInstance -> {
                evict(serviceManager, grayInstance);
            });
        });

    }


    private void evict(GrayServiceManager serviceManager, GrayInstance grayInstance){
        if(isDownline(grayInstance)){
            serviceManager.deleteGrayInstance(grayInstance.getServiceId(), grayInstance.getInstanceId());
        }
    }


    private boolean isDownline(GrayInstance grayInstance){
        Application app = eurekaClient.getApplication(grayInstance.getServiceId());
        return app==null || app.getByInstanceId(grayInstance.getInstanceId())==null;
    }
}
