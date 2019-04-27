package com.fm.gray.test;

import com.fm.cloud.bamboo.BambooRequest;
import com.fm.gray.core.GrayDecision;
import com.fm.gray.core.MultiGrayDecision;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class MultiGrayDecisionTest {




    @Test
    public void test(){
        MultiGrayDecision decision =new  MultiGrayDecision(new TestGrayDecision("a"));

        boolean r = decision.and(new TestGrayDecision("B")).and(new TestGrayDecision("C"))
                .test(null);
        System.out.println(r);


    }


    public static class TestGrayDecision implements GrayDecision {
        private String s;

        public TestGrayDecision(String s) {
            this.s = s;
        }

        @Override
        public boolean test(BambooRequest bambooRequest) {
            System.out.println(this);
            System.out.println("s= "+s);
            return StringUtils.isAllLowerCase(s);
        }
    }
}
