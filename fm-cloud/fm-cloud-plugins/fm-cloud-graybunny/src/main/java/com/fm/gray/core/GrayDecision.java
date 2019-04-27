package com.fm.gray.core;


import com.fm.cloud.bamboo.BambooRequest;

import java.util.function.Predicate;

public interface GrayDecision{

    boolean test(BambooRequest bambooRequest);



    static AllowGraydecision allow(){
        return AllowGraydecision.INSTANCE;
    }


    static RefuseGraydecision refuse(){
        return RefuseGraydecision.INSTANCE;
    }



    class AllowGraydecision implements GrayDecision{


        private static AllowGraydecision INSTANCE = new AllowGraydecision();

        private AllowGraydecision(){

        }


        @Override
        public boolean test(BambooRequest bambooRequest) {
            return true;
        }
    }




    class RefuseGraydecision implements GrayDecision{


        private static RefuseGraydecision INSTANCE = new RefuseGraydecision();

        private RefuseGraydecision(){

        }

        @Override
        public boolean test(BambooRequest bambooRequest) {
            return false;
        }
    }

}
