package com.fm.cloud.bamboo;


import com.fm.cloud.bamboo.ribbon.EurekaServerExtractor;

public class BambooAppContext {

    private static BambooRibbonConnectionPoint defaultConnectionPoint;
    private static EurekaServerExtractor eurekaServerExtractor;

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
}
