package com.cristian.callmessageprocessor.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Metrics {
    
    private long missingFields;
    private long blankContent;
    private long fieldErrors;
    private List<CountryOriginDestination> callsByOriginDestination;
    private Map<String, Long> callStatuses;
    private Map<String, Integer> avgCallDurationByCountry;
    private Map<String, Long> wordOccurrences;
    
    public Metrics() {
        callStatuses = new HashMap<>();
        avgCallDurationByCountry = new HashMap<>();
        callsByOriginDestination = new ArrayList<CountryOriginDestination>();
        wordOccurrences = new HashMap<>();
        callStatuses.put("OK", 0L);
        callStatuses.put("KO", 0L);
    }
}
