package com.cristian.callmessageprocessor.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cristian.callmessageprocessor.dto.CountryOriginDestination;
import com.cristian.callmessageprocessor.dto.KPIs;
import com.cristian.callmessageprocessor.dto.Metrics;
import com.cristian.callmessageprocessor.models.CallRecord;
import com.cristian.callmessageprocessor.models.Records;
import com.cristian.callmessageprocessor.models.TextRecord;
import com.cristian.callmessageprocessor.utils.KPIsUtils;
import com.cristian.callmessageprocessor.utils.MetricsUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MetricsAndKPIsService {
    
    public Metrics calculateMetrics(Records records) {
        Metrics metrics = new Metrics();
        long missingFields = 0;
        long blankContent = 0;
        long fieldErrors = 0;
        List<CountryOriginDestination> callsByOriginDestination = new ArrayList<>();
        Map<String, Long> wordFrequencyMap = new HashMap<>();

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
            
            metrics.setCallStatuses(MetricsUtils.getCallStatuses(metrics.getCallStatuses(), callRecord)); 

        }

        metrics.setAvgCallDurationByCountry(MetricsUtils.getAvgCallDurationByCountry(records.getCallRecords()));
        metrics.setCallsByOriginDestination(callsByOriginDestination);

        for (TextRecord textRecord : records.getTextRecords()) {
            //There is no more metrics checks for TextRecords so i will let the code here

            //breaking text into words based on any character other than a letter
            String[] words = textRecord.getMessage_content().split("[^a-zA-Z]+");

            for (String word : words) {
                //Filter blanks
                if (!word.isEmpty()) {
                    wordFrequencyMap.compute(word.toUpperCase(), (key, oldValue) -> (oldValue == null) ? 1 :oldValue +1);
                }   
            }
        }

        metrics.setWordOccurrences(wordFrequencyMap);
        return metrics;
    }

    public KPIs calculateKPIs(Records records) {
        KPIs kpis = new KPIs();
        //This method is used for only 1 specific file
        kpis.setTotalProcessedFiles(1);
        kpis.setTotalRows(records.getCallRecords().size() + records.getTextRecords().size() + records.getInvalidRecords().size());
        kpis.setTotalCalls(records.getCallRecords().size());
        kpis.setTotalMessages(records.getTextRecords().size());
        kpis.setDistinctOriginCountryCodes(KPIsUtils.getDistinctCodesNumber(records, true));
        kpis.setDistinctDestinationCountryCodes(KPIsUtils.getDistinctCodesNumber(records, false));
        return kpis;
    }

    
    
    
}
