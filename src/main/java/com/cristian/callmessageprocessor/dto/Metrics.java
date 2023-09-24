package com.cristian.callmessageprocessor.dto;

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
    private Map<String, Double> avgCallDurationByCountry;
    private Map<String, Long> wordOccurrences;
    
}
