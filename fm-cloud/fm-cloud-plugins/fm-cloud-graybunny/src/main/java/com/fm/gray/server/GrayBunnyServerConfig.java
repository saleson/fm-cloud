package com.fm.gray.server;

public interface GrayBunnyServerConfig {


    /**
     * 检查服务实例是否下线的间隔时间(ms)
     * @return
     */
    int getEvictionIntervalTimerInMs();


}
