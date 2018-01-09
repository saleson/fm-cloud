package com.fm.gray;

import com.fm.gray.core.GrayManager;

public class GrayBunnyAppContext {
    private static GrayManager grayManager;


    public static GrayManager getGrayManager() {
        return grayManager;
    }

    static void setGrayManager(GrayManager grayManager) {
        GrayBunnyAppContext.grayManager = grayManager;
    }
}
