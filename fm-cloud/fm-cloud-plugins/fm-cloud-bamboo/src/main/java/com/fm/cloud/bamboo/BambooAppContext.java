package com.fm.cloud.bamboo;


public class BambooAppContext {

    private static BambooRibbonConnectionPoint defaultConnectionPoint;

    public static BambooRibbonConnectionPoint getBambooRibbonConnectionPoint(){
        return defaultConnectionPoint;
    }


    static void setDefaultConnectionPoint(BambooRibbonConnectionPoint connectionPoint){
        BambooAppContext.defaultConnectionPoint = connectionPoint;
    }
}
