package com.fm.gray.server.config.properties;

import com.fm.gray.server.GrayBunnyServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "graybunny.server")
public class GrayBunnyServerConfigBean  implements GrayBunnyServerConfig{

    private int evictionIntervalTimerInMs = 60000;

    @Override
    public int getEvictionIntervalTimerInMs() {
        return evictionIntervalTimerInMs;
    }
}
