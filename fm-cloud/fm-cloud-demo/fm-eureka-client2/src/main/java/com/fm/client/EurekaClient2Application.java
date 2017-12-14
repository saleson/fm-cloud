package com.fm.client;

import com.fm.cloud.bamboo.BambooConfiguration;
import com.fm.cloud.bamboo.feign.BambooFeighConfiguration;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by saleson on 2017/10/18.
 */
@SpringBootApplication
@EnableDiscoveryClient
//@Import(BambooConfiguration.class)
@ImportAutoConfiguration({BambooConfiguration.class, BambooFeighConfiguration.class})
@EnableFeignClients
public class EurekaClient2Application {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EurekaClient2Application.class);


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }

    public static void main(String[] args) throws UnknownHostException {
        Environment env = new SpringApplicationBuilder(EurekaClient2Application.class).web(true).run(args).getEnvironment();
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"), env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));

        String configServerStatus = env.getProperty("configserver.status");
        log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
    }
}
