package com.fm.cloud.bamboo;

public interface RequestVersionExtractor {

    String extractVersion(BambooRequest  bambooRequest);


    public static class Default implements RequestVersionExtractor{
        private static final String VERSION = "version";

        @Override
        public String extractVersion(BambooRequest bambooRequest) {
            return bambooRequest.getParams().getFirst(VERSION);
        }
    }
}
