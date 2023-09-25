package com.cristian.callmessageprocessor.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.cristian.callmessageprocessor.dto.KPIs;
import com.cristian.callmessageprocessor.models.CallRecord;
import com.cristian.callmessageprocessor.models.Records;
import com.cristian.callmessageprocessor.models.TextRecord;

public class KPIsUtils {

    public static Set<String> getDistinctCodesNumber (Records records, boolean isOrigin) {

        Set<String> uniqueCountryCodes = new HashSet<>();

        //Take all the country codes from CallRecords
        for(CallRecord callRecord : records.getCallRecords()) {
            String countryCode;
            if (isOrigin) {
                countryCode = MSISDNCC.getCountryFromMSISDN(callRecord.getOrigin());
            } else {
                countryCode = MSISDNCC.getCountryFromMSISDN(callRecord.getDestination());
            }
            uniqueCountryCodes.add(countryCode);
        }

        //Do the same with TextRecords
        for (TextRecord textRecord : records.getTextRecords()) {
            String countryCode;
            if (isOrigin) {
                countryCode = MSISDNCC.getCountryFromMSISDN(textRecord.getOrigin());
            } else {
                countryCode = MSISDNCC.getCountryFromMSISDN(textRecord.getDestination());
            }
            uniqueCountryCodes.add(countryCode);
        }
        
        return uniqueCountryCodes;
    }

    public static KPIs getAllKPIs(Map<String, KPIs> KpisMap) {
        KPIs totalKpis = new KPIs();

        for(Entry<String, KPIs> entry : KpisMap.entrySet()) {
            KPIs kpis = entry.getValue();
            totalKpis.setTotalProcessedFiles(totalKpis.getTotalProcessedFiles() + 1);
            totalKpis.setTotalRows(totalKpis.getTotalRows() + kpis.getTotalRows());
            totalKpis.setTotalCalls(totalKpis.getTotalCalls() + kpis.getTotalCalls());
            totalKpis.setTotalMessages(totalKpis.getTotalMessages() + kpis.getTotalMessages());

            for (String country : kpis.getUniqueCountryCodesOrigin()) {
                totalKpis.getUniqueCountryCodesOrigin().add(country);
            }

            totalKpis.setDistinctOriginCountryCodes(totalKpis.getUniqueCountryCodesOrigin().size());

            for (String country : kpis.getUniqueCountryCodesDestination()) {
                totalKpis.getUniqueCountryCodesDestination().add(country);
            }

            totalKpis.setDistinctDestinationCountryCodes(totalKpis.getUniqueCountryCodesDestination().size());

            for (Entry<String, Long> process : kpis.getProcessDurations().entrySet()) {
                totalKpis.getProcessDurations().put(process.getKey(), process.getValue());
            }

        }

        return totalKpis;
    }

}
