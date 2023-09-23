package com.cristian.callmessageprocessor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

        //Test if date have a valid format
        if(!date.matches("^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$")) {
            log.error(date + " is a invalid date format. Use YYYYMMDD");
            throw new IllegalArgumentException();
        }

        String jsonUrl = baseUrl + "/MCP_" + date + ".json";

        //Download the data form the url
        try {
            String jsonContent = restTemplate.getForObject(jsonUrl, String.class);
            log.info("Downloaded JSON content for date {}", DateUtils.formatDate(date));
            return jsonContent;  
        } catch (HttpClientErrorException e) {
            log.error("JSON not found for date {}", DateUtils.formatDate(date));
            throw e;
        }
    }
}
