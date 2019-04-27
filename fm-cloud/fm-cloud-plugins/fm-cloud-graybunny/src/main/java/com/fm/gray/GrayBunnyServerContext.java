package com.fm.gray;

import com.fm.gray.core.GrayServiceManager;
import com.fm.gray.server.GrayBunnyServerEvictor;

public class GrayBunnyServerContext {

    public static final String DEFAULT_PREFIX = "gray";

    private static GrayServiceManager grayServiceManager;
    private static GrayBunnyServerEvictor grayBunnyServerEvictor;


    public static GrayServiceManager getGrayServiceManager() {
        return grayServiceManager;
    }

    static void setGrayServiceManager(GrayServiceManager grayServiceManager) {
        GrayBunnyServerContext.grayServiceManager = grayServiceManager;
    }

    public static GrayBunnyServerEvictor getGrayBunnyServerEvictor() {
        return grayBunnyServerEvictor;
    }

    static void setGrayBunnyServerEvictor(GrayBunnyServerEvictor grayBunnyServerEvictor) {
        GrayBunnyServerContext.grayBunnyServerEvictor = grayBunnyServerEvictor;
    }
}
