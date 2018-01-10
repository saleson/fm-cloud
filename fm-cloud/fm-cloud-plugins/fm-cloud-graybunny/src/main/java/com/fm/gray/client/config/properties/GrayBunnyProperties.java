package com.fm.gray.client.config.properties;

import com.fm.gray.client.GrayClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("graybunny")
public class GrayBunnyProperties implements GrayClientConfig{


    private int serviceUpdateIntervalTimerInMs = 60;


    private String informationClient = "http";

    private String serverUrl = "http://localhost:10202";


    public String getInformationClient() {
        return informationClient;
    }

    public void setInformationClient(String informationClient) {
        this.informationClient = informationClient;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    @Override
    public int getServiceUpdateIntervalTimerInMs() {
        return serviceUpdateIntervalTimerInMs;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
