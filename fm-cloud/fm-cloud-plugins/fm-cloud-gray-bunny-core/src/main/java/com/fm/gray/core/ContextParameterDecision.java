package com.fm.gray.core;

import com.fm.cloud.bamboo.BambooRequest;
import com.fm.cloud.bamboo.BambooRequestContext;
import org.apache.commons.collections.ListUtils;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContextParameterDecision implements GrayDecision {


    private final Map<String, String> params;

    public ContextParameterDecision(Map<String, String> params) {
        if (params.isEmpty()) {
            throw new NullPointerException("params must not be empty");
        }
        this.params = params;
    }

    @Override
    public boolean test(BambooRequest bambooRequest) {
        BambooRequestContext bambooRequestContext = BambooRequestContext.currentRequestCentxt();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!Objects.equals(entry.getValue(), bambooRequestContext.getParameter(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
