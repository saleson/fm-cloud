package com.fm.gray.client.config;

import com.fm.cloud.bamboo.config.BambooConfiguration;
import com.fm.gray.GrayBunnyInitializingBean;
import com.fm.gray.client.DefaultGrayDecisionFactory;
import com.fm.gray.client.DefaultGrayManager;
import com.fm.gray.client.GrayDecisionFactory;
import com.fm.gray.client.HttpInformationClient;
import com.fm.gray.client.config.properties.GrayBunnyProperties;
import com.fm.gray.core.GrayManager;
import com.fm.gray.core.InformationClient;
import com.fm.gray.ribbon.GrayLoadBalanceRule;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({GrayBunnyProperties.class})
public class GrayBunnyConfiguration {


    @Bean
    public BambooConfiguration.UnUseBambooIRule unUseBambooIRule() {
        return new BambooConfiguration.UnUseBambooIRule();
    }


    @Bean
    public IRule ribbonRule(@Autowired(required = false) IClientConfig config) {
        GrayLoadBalanceRule rule = new GrayLoadBalanceRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }

    @Bean
    public GrayBunnyInitializingBean grayBunnyInitializingBean(){
        return new GrayBunnyInitializingBean();
    }

    @Bean
    @ConditionalOnMissingBean
    public GrayDecisionFactory grayDecisionFactory(){
        return new DefaultGrayDecisionFactory();
    }


    @Configuration
    @ConditionalOnProperty(prefix = "graybunny", value = "information-client", havingValue = "http", matchIfMissing = true)
    public static class HttpGrayManagerClientConfiguration {
        @Autowired
        private GrayBunnyProperties grayBunnyProperties;

        @Bean
        public InformationClient informationClient() {
            return new HttpInformationClient(grayBunnyProperties.getServerUrl(), new RestTemplate());
        }


        @Bean
        public GrayManager grayManager(InformationClient informationClient, GrayDecisionFactory grayDecisionFactory) {
            return new DefaultGrayManager(informationClient, grayDecisionFactory);
        }
    }


}
