package com.fm.gray.client.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("graybunny")
public class GrayBunnyProperties {



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

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
