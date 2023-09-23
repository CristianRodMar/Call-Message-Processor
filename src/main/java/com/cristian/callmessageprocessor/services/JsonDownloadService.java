package com.cristian.callmessageprocessor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cristian.callmessageprocessor.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JsonDownloadService {
    
    @Value("${json.url.base}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public JsonDownloadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String downloadJsonFile(String date) {
        String jsonUrl = baseUrl + "/MCP_" + date + ".json";
        String jsonContent = restTemplate.getForObject(jsonUrl, String.class);
        log.info("Downloaded JSON content for date {}", DateUtils.formatDate(date));
        return jsonContent;
    }
}
