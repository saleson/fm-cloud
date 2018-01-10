package com.fm.gray.server.config;

import com.fm.cloud.bamboo.BambooConstants;
import com.fm.gray.DefaultGrayServiceManager;
import com.fm.gray.GrayBunnyServerInitializingBean;
import com.fm.gray.server.EurekaGrayBunnyServerEvictor;
import com.fm.gray.server.GrayBunnyServerConfig;
import com.fm.gray.server.GrayBunnyServerEvictor;
import com.fm.gray.server.NoActionGrayBunnyServerEvictor;
import com.fm.gray.server.config.properties.GrayBunnyServerConfigBean;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties({GrayBunnyServerConfigBean.class})
@Import(WebConfiguration.class)
public class GrayBunnyServerAutoConfiguration {

    @Autowired
    private GrayBunnyServerConfig grayBunnyServerConfig;

    @Bean
    @ConditionalOnMissingBean
    public DefaultGrayServiceManager defaultGrayServiceManager(){
        return new DefaultGrayServiceManager(grayBunnyServerConfig);
    }


    @Bean
    @Order(value = BambooConstants.INITIALIZING_ORDER + 1)
    public GrayBunnyServerInitializingBean grayBunnyServerInitializingBean(){
        return new GrayBunnyServerInitializingBean();
    }



    @Bean
    @ConditionalOnMissingBean
    public GrayBunnyServerEvictor grayBunnyServerEvictor(@Autowired(required = false) EurekaClient eurekaClient){
        return eurekaClient==null ? NoActionGrayBunnyServerEvictor.INSTANCE : new EurekaGrayBunnyServerEvictor(eurekaClient);
    }

}
