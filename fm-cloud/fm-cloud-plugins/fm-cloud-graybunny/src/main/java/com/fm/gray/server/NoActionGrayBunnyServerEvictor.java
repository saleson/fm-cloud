package com.fm.gray.server;

import com.fm.gray.core.GrayServiceManager;

public class NoActionGrayBunnyServerEvictor implements GrayBunnyServerEvictor{


    public static NoActionGrayBunnyServerEvictor INSTANCE = new NoActionGrayBunnyServerEvictor();


    private NoActionGrayBunnyServerEvictor(){

    }

    @Override
    public void evict(GrayServiceManager serviceManager) {

    }
}
