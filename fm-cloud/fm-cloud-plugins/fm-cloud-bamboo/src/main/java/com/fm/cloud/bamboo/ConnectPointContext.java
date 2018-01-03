package com.fm.cloud.bamboo;

public class ConnectPointContext {
    private BambooRequest bambooRequest;

    private ConnectPointContext(BambooRequest bambooRequest) {
        this.bambooRequest = bambooRequest;
    }

    public BambooRequest getBambooRequest() {
        return bambooRequest;
    }

    public void setBambooRequest(BambooRequest bambooRequest) {
        this.bambooRequest = bambooRequest;
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private BambooRequest bambooRequest;

        private Builder(){

        }


        public Builder bambooRequest(BambooRequest bambooRequest){
            this.bambooRequest = bambooRequest;
            return this;
        }

        public ConnectPointContext build(){
            return new ConnectPointContext(bambooRequest);
        }
    }
}
