package com.fm.gray.client;

import com.fm.gray.core.GrayInstance;
import com.fm.gray.core.GrayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseGrayManager extends AbstractGrayManager {
    private static final Logger log = LoggerFactory.getLogger(BaseGrayManager.class);
    private Map<String, GrayService> grayServiceMap;
    private Timer updateTimer = new Timer("GrayBunny-UpdateTimer", true);
    private GrayClientConfig clientConfig;

    public BaseGrayManager(GrayOptionalArgs grayOptionalArgs) {
        super(grayOptionalArgs.getInformationClient(), grayOptionalArgs.getDecisionFactory());
        clientConfig = grayOptionalArgs.getGrayClientConfig();
    }


    @Override
    public void openForWork() {
        listGrayService();
        updateTimer.schedule(new UpdateTask(),
                clientConfig.getServiceUpdateIntervalTimerInMs(),
                clientConfig.getServiceUpdateIntervalTimerInMs());
    }

    @Override
    public List<GrayService> listGrayService() {
        if (grayServiceMap == null) {
            List<GrayService> grayServices = super.listGrayService();
            if (grayServices == null) {
                return null;

            }
            updateGrayServices(grayServices);
        }
        return new ArrayList<>(grayServiceMap.values());
    }


    @Override
    public GrayService grayService(String serviceId) {
        if (grayServiceMap == null) {
            return super.grayService(serviceId);
        }
        return grayServiceMap.get(serviceId);
    }

    @Override
    public GrayInstance grayInstance(String serviceId, String instanceId) {
        if (grayServiceMap == null) {
            return super.grayInstance(serviceId, instanceId);
        }
        GrayService grayService = grayService(serviceId);
        if (grayService != null) {
            return grayService.getGrayInstance(instanceId);
        }
        return null;
    }

    @Override
    protected void serviceShutdown() {
        updateTimer.cancel();
    }

    @Override
    public void updateGrayServices(Collection<GrayService> grayServices) {
        if (grayServices == null) {
            return;
        }
        Map<String, GrayService> grayMap = new HashMap<>();
        grayServices.forEach(grayService -> grayMap.put(grayService.getServiceId(), grayService));
        grayServiceMap = new ConcurrentHashMap(grayMap);
    }


    private void doUpdate() {
        try {
            updateGrayServices(client.listGrayService());
        } catch (Exception e) {
            log.error("更新灰度服务列表失败", e);
        }
    }


    class UpdateTask extends TimerTask {

        @Override
        public void run() {
            doUpdate();
        }
    }


}
