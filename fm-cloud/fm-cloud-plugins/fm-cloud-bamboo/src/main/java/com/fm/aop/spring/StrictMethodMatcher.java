package com.fm.aop.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.MethodMatcher;

import java.lang.reflect.Method;

/**
 * 准确匹配指定的方法
 * Created by saleson on 2017/12/21.
 */
public class StrictMethodMatcher implements MethodMatcher {


    private Method specimen;
    private String methodName;
    private Class[] argTypes;


    /**
     * 指定要匹配的方法
     *
     * @param specimen
     */
    public StrictMethodMatcher(Method specimen) {
        this.specimen = specimen;
        this.methodName = specimen.getName();
        this.argTypes = specimen.getParameterTypes();
    }


    /**
     * 指定要匹配的方法名称和参数类型，需要完全一致
     *
     * @param methodName
     * @param argTypes   如果传null就不判断方法参数类型
     */
    public StrictMethodMatcher(String methodName, Class... argTypes) {
        this.methodName = methodName;
        this.argTypes = argTypes;
    }


    /**
     * 从目标类中匹配指定的方法
     *
     * @param targetClass
     * @param methodName
     * @param argTypes
     * @throws NoSuchMethodException
     */
    public StrictMethodMatcher(Class targetClass, String methodName, Class... argTypes) throws NoSuchMethodException {
        this(getExecuteWithLoadBalancerMethod(targetClass, methodName, argTypes));
    }


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (specimen != null) {
            return specimen.equals(method);
        }
        //argTypes为null就不判断方法参数类型
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


    /**
     * 匹配方法参数
     *
     * @param argTypes
     * @param parmeterTypes
     * @return
     */
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


    /**
     * 从类结构中找到方法
     *
     * @param targetClass
     * @param methodName
     * @param argTypes
     * @return
     * @throws NoSuchMethodException
     */
    private static Method getExecuteWithLoadBalancerMethod(Class targetClass, String methodName, Class[] argTypes) throws NoSuchMethodException {
        return targetClass.getMethod(methodName, argTypes);
    }
}
