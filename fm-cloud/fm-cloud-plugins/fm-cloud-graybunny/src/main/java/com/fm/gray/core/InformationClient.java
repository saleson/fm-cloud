package com.fm.gray.core;

import java.util.List;

public interface InformationClient {

    List<GrayService> listGrayService();

    GrayService grayService(String serviceId);

    GrayInstance grayInstance(String serviceId, String instanceId);
}
