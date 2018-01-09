package com.fm.gray.client;

import com.fm.gray.core.GrayDecision;
import com.fm.gray.core.GrayPolicy;

public interface GrayDecisionFactory {


    GrayDecision getDecision(GrayPolicy grayPolicy);
}
