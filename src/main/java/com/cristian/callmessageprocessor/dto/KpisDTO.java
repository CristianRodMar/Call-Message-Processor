package com.cristian.callmessageprocessor.dto;

import java.util.List;
import lombok.Data;

@Data
public class KpisDTO {
    private int totalProcessedFiles;
    private int totalRows;
    private int totalCalls;
    private int totalMessages;
    private int totalOriginCountryCodes;
    private int totalDestinationCountryCodes;
    private List<Long> jsonProcessingDuration;
}
