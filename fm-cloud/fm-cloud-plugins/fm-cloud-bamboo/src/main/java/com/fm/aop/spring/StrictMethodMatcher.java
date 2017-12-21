package com.fm.aop.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.MethodMatcher;

import java.lang.reflect.Method;

/**
 * Created by saleson on 2017/12/21.
 */
public class StrictMethodMatcher implements MethodMatcher {


    private Method specimen;
    private String methodName;
    private Class[] argTypes;


    public StrictMethodMatcher(Method specimen) {
        this.specimen = specimen;
        this.methodName = specimen.getName();
        this.argTypes = specimen.getParameterTypes();
    }


    /**
     * @param methodName
     * @param argTypes   如果传null就不判断方法参数类型
     */
    public StrictMethodMatcher(String methodName, Class... argTypes) {
        this.methodName = methodName;
        this.argTypes = argTypes;
    }


    public StrictMethodMatcher(Class targetClass, String methodName, Class... argTypes) throws NoSuchMethodException {
        this(getExecuteWithLoadBalancerMethod(targetClass, methodName, argTypes));
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (specimen != null) {
            return specimen.equals(method);
        }
        if (StringUtils.equals(methodName, method.getName())) {
            return argTypes == null || matchesParameterTypes(argTypes, method.getParameterTypes());
        }
        return false;
    }

    @Override
    public boolean isRuntime() {
        return true;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return true;
    }


    private boolean matchesParameterTypes(Class[] argTypes, Class[] parmeterTypes) {
        if (parmeterTypes.length != argTypes.length) {
            return false;
        }
        for (int i = 0; i < argTypes.length; i++) {
            if (!argTypes[i].equals(parmeterTypes[i])) {
                return false;
            }
        }
        return true;
    }


    private static Method getExecuteWithLoadBalancerMethod(Class targetClass, String methodName, Class[] argTypes) throws NoSuchMethodException {
        return targetClass.getMethod(methodName, argTypes);
    }
}
