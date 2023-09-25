package com.cristian.callmessageprocessor.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KPIs {

    private long totalProcessedFiles;
    private long totalRows;
    private long totalCalls;
    private long totalMessages;
    private long distinctOriginCountryCodes;
    private long distinctDestinationCountryCodes;
    private Map<String, Long> processDurations = new HashMap<>();
    @JsonIgnore
    private Set<String> uniqueCountryCodesOrigin = new HashSet<>();
    @JsonIgnore
    private Set<String> uniqueCountryCodesDestination = new HashSet<>();
    
}
