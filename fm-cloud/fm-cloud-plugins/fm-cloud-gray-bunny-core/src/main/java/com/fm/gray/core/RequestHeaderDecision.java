package com.fm.gray.core;

import com.fm.cloud.bamboo.BambooRequest;
import org.apache.commons.collections.ListUtils;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

public class RequestHeaderDecision implements GrayDecision{


    private final MultiValueMap<String, String> headers;

    public RequestHeaderDecision(MultiValueMap<String, String> headers) {
        if(headers.isEmpty()){
            throw new NullPointerException("headers must not be empty");
        }
        this.headers = headers;
    }

    @Override
    public boolean test(BambooRequest bambooRequest) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = bambooRequest.getHeaders().get(entry.getKey());
            if(!ListUtils.isEqualList(entry.getValue(), values)){
                return false;
            }
        }
        return true;
    }
}
