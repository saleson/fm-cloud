package com.fm.gray.client;

import com.fm.gray.core.GrayInstance;
import com.fm.gray.core.GrayService;
import com.fm.gray.core.InformationClient;

import java.util.List;

public class RedisInformationClient implements InformationClient{
    @Override
    public List<GrayService> listGrayService() {
        return null;
    }

    @Override
    public GrayService grayService(String serviceId) {
        return null;
    }

    @Override
    public GrayInstance grayInstance(String serviceId, String instanceId) {
        return null;
    }
}
