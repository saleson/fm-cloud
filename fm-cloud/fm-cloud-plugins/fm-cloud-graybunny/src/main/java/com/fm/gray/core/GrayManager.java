package com.fm.gray.core;

import java.util.Collection;
import java.util.List;

public interface GrayManager {

    void openForWork();

    boolean isOpen(String serviceId);

    List<GrayService> listGrayService();

    GrayService grayService(String serviceId);

    GrayInstance grayInstance(String serviceId, String instanceId);

    List<GrayDecision> grayDecision(GrayInstance instance);

    List<GrayDecision> grayDecision(String serviceId, String instanceId);

    void updateGrayServices(Collection<GrayService> grayServices);

    void serviceDownline();
}
