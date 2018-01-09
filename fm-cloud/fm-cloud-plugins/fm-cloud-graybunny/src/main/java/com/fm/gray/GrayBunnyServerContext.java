package com.fm.gray;

import com.fm.gray.core.GrayServiceManager;

public class GrayBunnyServerContext {

    public static final String DEFAULT_PREFIX = "gray";

    private static GrayServiceManager grayServiceManager;


    public static GrayServiceManager getGrayServiceManager() {
        return grayServiceManager;
    }

    static void setGrayServiceManager(GrayServiceManager grayServiceManager) {
        GrayBunnyServerContext.grayServiceManager = grayServiceManager;
    }
}
