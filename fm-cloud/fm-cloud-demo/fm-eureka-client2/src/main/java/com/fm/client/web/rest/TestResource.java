package com.fm.client.web.rest;

import com.fm.client.feign.TestClient;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by saleson on 2017/10/18.
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestClient testClient;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> testGet(HttpServletRequest request) {
        String url = "http://eureka-client/api/test/get";
        String query = request.getQueryString();
        if (!StringUtils.isEmpty(query)) {
            url = url + "?" + query;
        }
//        if (!StringUtils.isEmpty(version)) {
//            url += "?version=" + version;
//        }
        Map map = restTemplate.getForObject(url, Map.class);
        if (map != null) {
            return map;
        } else {
            return ImmutableMap.of("test2", "success.");
        }
    }

    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> testGet2(@RequestParam(value = "version", required = false) String version) {
        Map map = testClient.testGet(version);
        if (map != null) {
            return map;
        } else {
            return ImmutableMap.of("test2", "success.");
        }
    }

}
