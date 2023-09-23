package com.cristian.callmessageprocessor.dto;

import java.util.Map;
import lombok.Data;

@Data
public class MetricsDTO {
    private int missingFields;
    private int blankContentMessages;
    private int fieldErrors;
    private Map<String, Integer> callCountByCountry;
    private double okKoCallRatio;
    private Map<String, Double> averageCallDurationByCountry;
    private Map<String, Integer> wordOccurrenceRanking;
}
