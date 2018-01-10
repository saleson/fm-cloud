package com.fm.gray.client;

import com.fm.gray.core.GrayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseGrayManager extends AbstractGrayManager {
    private static final Logger log = LoggerFactory.getLogger(BaseGrayManager.class);
    private Map<String, GrayService> grayServiceMap = new ConcurrentHashMap<>();
    private Timer updateTimer = new Timer("GrayBunny-UpdateTimer", true);
    private GrayClientConfig clientConfig;

    public BaseGrayManager(GrayOptionalArgs grayOptionalArgs) {
        super(grayOptionalArgs.getInformationClient(), grayOptionalArgs.getDecisionFactory());
        clientConfig = grayOptionalArgs.getGrayClientConfig();
    }


    @Override
    public void openForWork() {
        updateTimer.schedule(new UpdateTask(),
                clientConfig.getServiceUpdateIntervalTimerInMs(),
                clientConfig.getServiceUpdateIntervalTimerInMs());
    }

    @Override
    public List<GrayService> listGrayService() {
        if (grayServiceMap == null) {
            updateGrayServices(super.listGrayService());
        }
        return new ArrayList<>(grayServiceMap.values());
    }

    @Override
    protected void serviceShutdown() {
        updateTimer.cancel();
    }

    @Override
    public void updateGrayServices(Collection<GrayService> grayServices) {
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
