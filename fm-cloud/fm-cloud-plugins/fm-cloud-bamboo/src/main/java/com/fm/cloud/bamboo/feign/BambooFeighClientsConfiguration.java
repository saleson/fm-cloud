package com.fm.cloud.bamboo.feign;

import com.fm.cloud.bamboo.feign.ribbon.BambooFeignClient;
import feign.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BambooFeighClientsConfiguration {


    @Autowired
    private Client feignClient;

    @Bean
    public Client bambooFeignClient(){
        return new BambooFeignClient(feignClient);
    }

}
