package com.fm.gray.core;

import java.util.List;

public interface GrayManager {

    boolean isOpen(String serviceId);

    List<GrayService> listGrayService();

    GrayService grayService(String serviceId);

    GrayInstance grayInstance(String serviceId, String instanceId);

    List<GrayDecision> grayDecision(GrayInstance instance);

    List<GrayDecision> grayDecision(String serviceId, String instanceId);

    void serviceDownline();
}
