package com.fm.gray;

import com.fm.gray.client.InstanceLocalInfo;
import com.fm.gray.core.GrayManager;

public class GrayBunnyAppContext {
    private static GrayManager grayManager;
    private static InstanceLocalInfo instanceLocalInfo;


    public static GrayManager getGrayManager() {
        return grayManager;
    }

    static void setGrayManager(GrayManager grayManager) {
        GrayBunnyAppContext.grayManager = grayManager;
    }


    public static InstanceLocalInfo getInstanceLocalInfo() {
        return instanceLocalInfo;
    }

    static void setInstanceLocalInfo(InstanceLocalInfo instanceLocalInfo) {
        GrayBunnyAppContext.instanceLocalInfo = instanceLocalInfo;
    }
}
