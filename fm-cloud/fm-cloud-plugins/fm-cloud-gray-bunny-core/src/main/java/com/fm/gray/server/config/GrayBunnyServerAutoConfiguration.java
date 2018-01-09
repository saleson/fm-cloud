package com.fm.gray.server.config;

import com.fm.gray.DefaultGrayServiceManager;
import com.fm.gray.GrayBunnyServerInitializingBean;
import com.fm.gray.core.GrayServiceManager;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EurekaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.*;

@Configuration
@Import(WebConfiguration.class)
public class GrayBunnyServerAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public DefaultGrayServiceManager defaultGrayServiceManager(){
        return new DefaultGrayServiceManager();
    }


    @Bean
    public GrayBunnyServerInitializingBean grayBunnyServerInitializingBean(){
        return new GrayBunnyServerInitializingBean();
    }

}
