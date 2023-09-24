package com.cristian.callmessageprocessor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cristian.callmessageprocessor.dto.CountryOriginDestination;
import com.cristian.callmessageprocessor.dto.KPIs;
import com.cristian.callmessageprocessor.dto.Metrics;
import com.cristian.callmessageprocessor.models.CallRecord;
import com.cristian.callmessageprocessor.models.Records;
import com.cristian.callmessageprocessor.utils.MSISDNCC;
import com.cristian.callmessageprocessor.utils.MetricsUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MetricsAndKPIsService {
    
    public Metrics calculateMetrics(Records records) {
        Metrics metrics = new Metrics();
        long missingFields = 0;
        long blankContent = 0;
        long fieldErrors = 0;
        List<CountryOriginDestination> callsByOriginDestination = new ArrayList<>();

        for (Map<String, Object> invalidRecord : records.getInvalidRecords()) {

            if (MetricsUtils.haveMissingFields(invalidRecord)) {
                missingFields++;
            }

            if (MetricsUtils.haveMessageWithBlankContent(invalidRecord)) {
                blankContent++;
            }

            if (MetricsUtils.haveErrorFields(invalidRecord)) {
                fieldErrors++;
            }
        }

        metrics.setMissingFields(missingFields);
        metrics.setBlankContent(blankContent);
        metrics.setFieldErrors(fieldErrors);

        for (CallRecord callRecord : records.getCallRecords()) {
  
            callsByOriginDestination = MetricsUtils.getCallsByOriginDestination(callsByOriginDestination, callRecord);
            
        }

        metrics.setCallsByOriginDestination(callsByOriginDestination);

        return metrics;
    }

    public KPIs calculateKPIs(Records records) {
        KPIs kpis = new KPIs();
        return kpis;
    }

    
    
    
}
