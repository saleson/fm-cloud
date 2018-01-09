package com.fm.gray.client;

import com.fm.gray.core.GrayInstance;
import com.fm.gray.core.GrayService;
import com.fm.gray.core.InformationClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpInformationClient implements InformationClient{

    private final String baseUrl;
    private RestTemplate rest;

    public HttpInformationClient(String baseUrl, RestTemplate rest) {
        this.baseUrl = baseUrl;
        this.rest = rest;
    }

    @Override
    public List<GrayService> listGrayService() {
        String url = this.baseUrl +"/gray/services";

        ParameterizedTypeReference<List<GrayService>> typeRef = new ParameterizedTypeReference<List<GrayService>>() {
        };

        ResponseEntity<List<GrayService>> responseEntity = rest.exchange(url, HttpMethod.GET, null, typeRef);
        return  responseEntity.getBody();
    }

    @Override
    public GrayService grayService(String serviceId) {
        String url = this.baseUrl +"/gray/services/{serviceId}";
        Map<String, String> params = new HashMap<>();
        params.put("serviceId", serviceId);

        ResponseEntity<GrayService> responseEntity = rest.getForEntity(url, GrayService.class, params);
        return responseEntity.getBody();
    }

    @Override
    public GrayInstance grayInstance(String serviceId, String instanceId) {
        String url = this.baseUrl +"/gray/services/{serviceId}/instance/?instanceId={instanceId}";

        Map<String, String> params = new HashMap<>();
        params.put("serviceId", serviceId);
        params.put("instanceId", instanceId);

        ResponseEntity<GrayInstance> responseEntity = rest.getForEntity(url, GrayInstance.class, params);
        return responseEntity.getBody();
    }
}
