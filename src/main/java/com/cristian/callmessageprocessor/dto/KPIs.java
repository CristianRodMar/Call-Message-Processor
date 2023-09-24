package com.cristian.callmessageprocessor.dto;

import java.util.HashMap;
import java.util.Map;

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
    
}
