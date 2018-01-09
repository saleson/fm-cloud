package com.fm.gray.core;

import com.fm.cloud.bamboo.BambooRequest;

public class MultiGrayDecision implements GrayDecision {

    private GrayDecision decision;


    public MultiGrayDecision(GrayDecision decision) {
        this.decision = decision;
    }


    public MultiGrayDecision and(GrayDecision other){
        GrayDecision cur = decision;
        decision = t->  cur.test(t) && other.test(t);
        return this;
    }

    @Override
    public boolean test(BambooRequest bambooRequest) {
        return decision.test(bambooRequest);
    }


}
