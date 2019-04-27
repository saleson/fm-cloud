package com.fm.cloud.bamboo.feign;

import com.netflix.loadbalancer.ILoadBalancer;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created by saleson on 2017/11/9.
 */
@ConditionalOnClass({ILoadBalancer.class, Feign.class})
@Configuration
@EnableFeignClients(defaultConfiguration = {BambooFeighClientsConfiguration.class})
public class BambooFeighConfiguration {

}
