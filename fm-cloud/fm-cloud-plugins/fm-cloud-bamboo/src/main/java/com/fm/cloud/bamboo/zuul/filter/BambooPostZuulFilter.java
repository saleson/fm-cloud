package com.fm.cloud.bamboo.zuul.filter;

import com.fm.cloud.bamboo.BambooRequestContext;
import com.netflix.zuul.ZuulFilter;

/**
 * 做一些善后工作。比如删除BambooRequestContext在ThreadLocal中的信息。
 */
public class BambooPostZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        BambooRequestContext.shutdownRequestContext();
        return null;
    }
}
