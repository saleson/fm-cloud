package com.fm.cloud.bamboo.config;

import com.fm.aop.spring.StrictMethodMatcher;
import com.fm.aop.spring.framework.autoproxy.AdvisorAutoProxyCreator;
import com.fm.cloud.bamboo.ribbon.BambooRequestContext;
import com.fm.cloud.bamboo.ribbon.loadbalancer.BambooLoadBalancerKey;
import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.ClientRequest;
import com.netflix.client.config.IClientConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.cloud.netflix.ribbon.support.ContextAwareRequest;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by saleson on 2017/12/21.
 */
@Deprecated
@Configuration
public class BambooExtConfigration {

    //    @Bean
//    public LoadBalancerAwareClientBeanPostProcessor loadBalancerAwareClientBeanPostProcessor() {
//        return new LoadBalancerAwareClientBeanPostProcessor();
//    }


    /**
     * 添加AbstractLoadBalancerAwareClient代理，用于创建BambooLoadBalancerKey缓存到线程中
     *
     * @return
     * @throws NoSuchMethodException
     */
    @Bean
    public AdvisorAutoProxyCreator loadBalancerAwareClientProxyCreator() throws NoSuchMethodException {
        ComposablePointcut pointcut = new ComposablePointcut(new RootClassFilter(AbstractLoadBalancerAwareClient.class),
                new StrictMethodMatcher(AbstractLoadBalancerAwareClient.class,
                        "executeWithLoadBalancer", ClientRequest.class, IClientConfig.class));

        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut,
                new ExecuteWithLoadBalancerMethodInterceptor());

        AdvisorAutoProxyCreator proxyCreator = new AdvisorAutoProxyCreator();
        proxyCreator.addAdvisor(pointcutAdvisor);
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }


    /**
     * 获取http request请求的接口version，并创建BambooLoadBalancerKey缓存到线程中
     */
    public static class ExecuteWithLoadBalancerMethodInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            ClientRequest request = (ClientRequest) invocation.getArguments()[0];
            if (request instanceof ContextAwareRequest) {

                RibbonCommandContext commandContext = ((ContextAwareRequest) request).getContext();
                String apiVersion = commandContext.getParams().getFirst("version");
                if (StringUtils.isNotEmpty(apiVersion)) {
                    String serviceId = commandContext.getServiceId();

                    BambooLoadBalancerKey loadBalancerKey = BambooLoadBalancerKey.builder()
                            .apiVersion(apiVersion).serviceId(serviceId).build();
                    BambooRequestContext.builder().request(request).loadBalancerKey(loadBalancerKey)
                            .build().toThreadLocal();
                    try {
                        return invocation.proceed();
                    } finally {
                        if (BambooRequestContext.instance() != null) {
                            BambooRequestContext.instance().removeByThreadLocal();
                        }
                    }
                }
            }
            return invocation.proceed();
        }
    }
}
