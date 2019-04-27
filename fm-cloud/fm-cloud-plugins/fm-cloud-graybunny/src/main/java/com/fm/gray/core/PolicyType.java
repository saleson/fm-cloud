package com.fm.gray.core;

public enum PolicyType {

    /**
     * @see com.fm.cloud.bamboo.BambooRequest#params
     */
    REQUEST_PARAMETER,
    /**
     * @see com.fm.cloud.bamboo.BambooRequest#headers
     */
    REQUEST_HEADER,

    /**
     * @see com.fm.cloud.bamboo.BambooRequest#ip
     */
    REQUEST_IP,
    /**
     * @see com.fm.cloud.bamboo.BambooRequestContext#params
     */
    CONTEXT_PARAMS
}
