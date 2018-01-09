package com.fm.gray.test;

import com.fm.gray.client.HttpInformationClient;
import com.fm.gray.core.GrayInstance;
import com.fm.gray.core.GrayService;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class HttpInformationClientTest {


    @Test
    public void test(){
        HttpInformationClient client = new HttpInformationClient("http://192.168.6.22:10202", new RestTemplate());
        GrayService grayService = client.grayService("eureka-client");
        System.out.println(grayService);
    }

    @Test
    public void test1(){
        HttpInformationClient client = new HttpInformationClient("http://192.168.6.22:10202", new RestTemplate());
        GrayInstance instance = client.grayInstance("eureka-client", "192.168.6.22:eureka-client:10101");
        System.out.println(instance);
    }
}
