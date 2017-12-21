package com.fm.cloud.bamboo.ribbon;

import com.fm.cloud.bamboo.ribbon.loadbalancer.BambooLoadBalancerKey;
import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.ClientRequest;
import com.netflix.client.config.IClientConfig;
import org.mockito.cglib.proxy.Callback;
import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.netflix.ribbon.support.ContextAwareRequest;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandContext;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by saleson on 2017/12/20.
 */
public class LoadBalancerAwareClientBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractLoadBalancerAwareClient) {
            LoadBalancerAwareClientProxy proxy = new LoadBalancerAwareClientProxy();
            proxy.executeWithLoadBalancerMethod = getExecuteWithLoadBalancerMethod((AbstractLoadBalancerAwareClient) bean);
            proxy.target = bean;
            //通过生成子类的方式创建代理类
            return proxy.getProxy(bean.getClass());
        }
        return bean;
    }

    private Method getExecuteWithLoadBalancerMethod(AbstractLoadBalancerAwareClient bean) {
        try {
            return bean.getClass().getMethod("executeWithLoadBalancer", ClientRequest.class, IClientConfig.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }


    public class LoadBalancerAwareClientProxy implements MethodInterceptor {

        private Method executeWithLoadBalancerMethod;
        private Object target;

        private Enhancer enhancer = new Enhancer();

        public Object getProxy(Class clazz) {
            //设置需要创建子类的类
            enhancer.setSuperclass(clazz);
            enhancer.setCallbacks(new Callback[]{this, org.mockito.cglib.proxy.NoOp.INSTANCE});
            //通过字节码技术动态创建子类实例
            enhancer.setCallbackFilter((method) -> {
                if (Modifier.isPrivate(method.getModifiers())) {
                    return 1;
                }
                return 0;
            });
            return enhancer.create();
        }

        //实现MethodInterceptor接口方法
        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy proxy) throws Throwable {
            if (method.equals(executeWithLoadBalancerMethod)) {
                ClientRequest request = (ClientRequest) args[0];
                if (request instanceof ContextAwareRequest) {
                    RibbonCommandContext commandContext = ((ContextAwareRequest) request).getContext();
                    String apiVersion = commandContext.getParams().getFirst("version");
                    String serviceId = commandContext.getServiceId();

                    BambooLoadBalancerKey loadBalancerKey = BambooLoadBalancerKey.builder()
                            .apiVersion(apiVersion).serviceId(serviceId).build();
                    BambooRequestContext.builder().request(request).loadBalancerKey(loadBalancerKey)
                            .build().toThreadLocal();
                }
            }
            //通过代理类调用父类中的方法
            Object result;
            try {
                if (Modifier.isPublic(method.getModifiers())) {
                    result = proxy.invoke(target, args);
                } else {
                    method.setAccessible(true);
                    result = method.invoke(target, args);
                }
            } finally {
                if (BambooRequestContext.instance() != null) {
                    BambooRequestContext.instance().removeByThreadLocal();
                }
            }
            return result;
        }
    }

}
