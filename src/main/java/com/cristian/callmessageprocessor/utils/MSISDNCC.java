package com.cristian.callmessageprocessor.utils;

import java.util.HashMap;
import java.util.Map;

public class MSISDNCC {

    private static final Map<String, String> countryCodeMap = new HashMap<>();

    static {
        countryCodeMap.put("34", "SPAIN");
        countryCodeMap.put("49", "GERMANY");
        countryCodeMap.put("44", "UNITED KINGDOM");
        countryCodeMap.put("39", "ITALY");
        countryCodeMap.put("33", "FRANCE");
    }
    
    public static String getCountryFromMSISDN (String MSISDN) {
        for (Map.Entry<String, String> entry : countryCodeMap.entrySet()) {
            String countryCode = entry.getKey();
            if (MSISDN.startsWith(countryCode)) {
                return entry.getValue();
            }
        }
        return "UNKNOWN";
    }
}
