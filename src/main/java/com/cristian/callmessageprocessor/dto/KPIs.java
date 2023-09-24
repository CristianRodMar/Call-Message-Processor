package com.cristian.callmessageprocessor.dto;

import java.util.List;

import lombok.Data;

@Data
public class KPIs {

    private long totalProcessedFiles;
    private long totalRows;
    private long totalCalls;
    private long totalMessages;
    private long distinctOriginCountryCodes;
    private long distinctDestinationCountryCodes;
    private List<Long> processDurations;
    
}
