package com.fm.cloud.bamboo.zuul;

import com.fm.cloud.bamboo.zuul.filter.BambooPostZuulFilter;
import com.fm.cloud.bamboo.zuul.filter.BambooPreZuulFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "com.netflix.zuul.http.ZuulServlet")
public class BambooZuulConfiguration {

    @Bean
    public BambooPreZuulFilter bambooPreZuulFilter(){
        return new BambooPreZuulFilter();
    }

    @Bean
    public BambooPostZuulFilter bambooPostZuulFilter(){
        return new BambooPostZuulFilter();
    }
}
