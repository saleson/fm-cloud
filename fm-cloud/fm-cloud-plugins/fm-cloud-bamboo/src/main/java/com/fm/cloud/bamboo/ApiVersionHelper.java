package com.fm.cloud.bamboo;

import com.google.common.collect.ImmutableMap;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saleson on 2017/11/13.
 */
@Deprecated
public class ApiVersionHelper {

    public static String getApiVersion(URI uri) {
        String query = uri.getQuery();
        return queryString2Map(query).get("version");
    }


    /**
     * queryString转Map
     *
     * @param queryStr example: jkd=32&32=&23
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> queryString2Map(String queryStr) {
        if (StringUtils.isEmpty(queryStr)) {
            return ImmutableMap.of();
        }
        Map<String, String> params = new HashMap<>();
        String[] entrys = queryStr.split("&");
        for (String entry : entrys) {
            if (StringUtils.isEmpty(entry)) {
                continue;
            }
            int len = entry.indexOf("=");
            if (len == -1) {
                params.put(entry, null);
            } else {
                String key = entry.substring(0, len);
                String value = entry.substring(len + 1, entry.length());
                params.put(key, value);
            }
        }

        return params;
    }
}
