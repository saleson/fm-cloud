package com.fm.cloud.bamboo.feign.ribbon;

import com.fm.cloud.bamboo.BambooRequest;
import com.fm.cloud.bamboo.BambooRequestContext;
import com.fm.utils.WebUtils;
import feign.Client;
import feign.Request;
import feign.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

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

        BambooRequest bambooRequest = builder.build();
        BambooRequestContext.initRequestContext(bambooRequest, bambooRequest.getParams().getFirst("version"));
        try {
            return delegate.execute(request, options);
        }finally {
            BambooRequestContext.shutdownRequestContext();
        }
    }
}
