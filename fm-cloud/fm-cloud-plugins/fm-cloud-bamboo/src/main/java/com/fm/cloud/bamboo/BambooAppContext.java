package com.fm.cloud.bamboo;


import com.fm.cloud.bamboo.ribbon.EurekaServerExtractor;

public class BambooAppContext {

    private static BambooRibbonConnectionPoint defaultConnectionPoint;
    private static EurekaServerExtractor eurekaServerExtractor;
    private static String localIp;

    public static BambooRibbonConnectionPoint getBambooRibbonConnectionPoint(){
        return defaultConnectionPoint;
    }


    static void setDefaultConnectionPoint(BambooRibbonConnectionPoint connectionPoint){
        BambooAppContext.defaultConnectionPoint = connectionPoint;
    }

    public static EurekaServerExtractor getEurekaServerExtractor() {
        return eurekaServerExtractor;
    }

    static void setEurekaServerExtractor(EurekaServerExtractor eurekaServerExtractor) {
        BambooAppContext.eurekaServerExtractor = eurekaServerExtractor;
    }

    public static String getLocalIp() {
        return localIp;
    }

    static void setLocalIp(String localIp) {
        BambooAppContext.localIp = localIp;
    }
}
