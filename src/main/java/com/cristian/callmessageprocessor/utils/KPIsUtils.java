package com.cristian.callmessageprocessor.utils;

import java.util.HashSet;
import java.util.Set;

import com.cristian.callmessageprocessor.models.CallRecord;
import com.cristian.callmessageprocessor.models.Records;
import com.cristian.callmessageprocessor.models.TextRecord;

public class KPIsUtils {

    public static long getDistinctCodesNumber (Records records, boolean isOrigin) {
        long distinctCountries = 0;

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
        
        distinctCountries = uniqueCountryCodes.size();
        return distinctCountries;
    }

}
