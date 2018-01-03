package com.fm.cloud.bamboo.feign.ribbon;

import com.fm.cloud.bamboo.BambooAppContext;
import com.fm.cloud.bamboo.BambooRequest;
import com.fm.cloud.bamboo.BambooRequestContext;
import com.fm.cloud.bamboo.ConnectPointContext;
import com.fm.utils.WebUtils;
import feign.Client;
import feign.Request;
import feign.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * 主要作用是用来获取request的相关信息，为后面的路由提供数据基础。
 */
public class BambooFeignClient implements Client {

    private Client delegate;


    public BambooFeignClient(Client delegate) {
        this.delegate = delegate;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        URI uri = URI.create(request.url());
        BambooRequest.Builder builder = BambooRequest.builder()
                .serviceId(uri.getHost())
                .uri(uri.getPath())
                .addMultiParams(WebUtils.getQueryParams(uri.getQuery()));

        request.headers().entrySet().forEach(entry ->{
            for (String v : entry.getValue()) {
                builder.addHeader(entry.getKey(), v);
            }
        });

        ConnectPointContext connectPointContext = ConnectPointContext.builder().bambooRequest(builder.build()).build();

        BambooAppContext.getBambooRibbonConnectionPoint().executeConnectPoint(connectPointContext);
//        BambooRequestContext.initRequestContext(bambooRequest, bambooRequest.getParams().getFirst("version"));
        try {
            return delegate.execute(request, options);
        }finally {
            BambooAppContext.getBambooRibbonConnectionPoint().shutdownconnectPoint();
        }
    }
}
