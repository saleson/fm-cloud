package com.fm.cloud.bamboo.web;

import com.fm.utils.WebUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IpKeepInterceptor extends HandlerInterceptorAdapter{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = WebUtils.getIpAddr(request);
        RequestIpKeeper.instance().setIp(ip);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestIpKeeper.instance().clear();
        super.afterCompletion(request, response, handler, ex);
    }
}
