package com.cristian.callmessageprocessor.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cristian.callmessageprocessor.dto.CountryOriginDestination;
import com.cristian.callmessageprocessor.models.CallRecord;

public class MetricsUtils {
    
    public static boolean haveMissingFields(Map<String, Object> invalidRecord) {

        String messageType;
        
        //Check if message_type is empty
        Object messageTypeObj = invalidRecord.get("message_type");
        if (messageTypeObj != null) {
            //It can have a wrong value but not empty
            messageType = String.valueOf(messageTypeObj);
            if (messageType.isEmpty() || messageType.isBlank()) {
                return true;
            }
        } else {
            //If the attribute is not present in the JSON I consider it missing
            return true;
        }

        //Check if timestamp is empty
        Object timestampObj = invalidRecord.get("timestamp");
        if (timestampObj != null) {
            String timestamp = String.valueOf(timestampObj);
            if (timestamp.isEmpty() || timestamp.isBlank()) {
                return true;
            }
        } else {
            return true;
        }

        //Check if origin is empty
        Object originObj = invalidRecord.get("origin");
        if (originObj != null) {
            String origin = String.valueOf(originObj);
            if (origin.isEmpty() || origin.isBlank()) {
                return true;
            }
        } else {
            return true;
        }

        //Check if destination is empty
        Object destinationObj = invalidRecord.get("destination");
        if (destinationObj != null) {
            String destination = String.valueOf(destinationObj);
            if (destination.isEmpty() || destination.isBlank()) {
                return true;
            }
        } else {
            return true;
        }

        //Check specific values of CALL type or MSG type
        if (messageType.equals("CALL")) {

            //Check if duration is empty
            Object durationObj = invalidRecord.get("duration");
            if (durationObj != null) {
                String duration = String.valueOf(durationObj);
                if (duration.isEmpty() || duration.isBlank()) {
                    return true;
                }
            } else {
                return true;
            }

            //Check if status_code is empty
            Object statusCodeObj = invalidRecord.get("status_code");
            if (statusCodeObj != null) {
                String statusCode = String.valueOf(statusCodeObj);
                if (statusCode.isEmpty() || statusCode.isBlank()) {
                    return true;
                }
            } else {
                return true;
            }

            //Check if status_description is empty
            Object statusDescObj = invalidRecord.get("status_description");
            if (statusDescObj != null) {
                String statusDesc = String.valueOf(statusDescObj);
                if (statusDesc.isEmpty() || statusDesc.isBlank()) {
                    return true;
                }
            } else {
                return true;
            }


        }

        if (messageType.equals("MSG")) {

            //Check if message_content is empty
            Object messageContentObj = invalidRecord.get("message_content");
            if (messageContentObj != null) {
                String messageContent = String.valueOf(messageContentObj);
                if (messageContent.isEmpty() || messageContent.isBlank()) {
                    return true;
                }
            } else {
                return true;
            }

            //Check if message_status is empty
            Object messageStatusObj = invalidRecord.get("message_status");
            if (messageStatusObj != null) {
                String messageStatus = String.valueOf(messageStatusObj);
                if (messageStatus.isEmpty() || messageStatus.isBlank()) {
                    return true;
                }
            } else {
                return true;
            }
        }   
    
        return false;
    }

    public static boolean haveMessageWithBlankContent(Map<String, Object> invalidRecord) {
        String messageType;
        //Checking if it has a message_type.
        Object messageTypeObj = invalidRecord.get("message_type");
        if (messageTypeObj != null) {
            messageType = String.valueOf(messageTypeObj);
            //Check if is a MSG type because CALL types don't have message_content
            if (messageType.equals("MSG")) {
                Object messageContentObj = invalidRecord.get("message_content");
                if (messageContentObj != null) {
                    String messageContent = String.valueOf(messageContentObj);
                    if (messageContent.isEmpty() || messageContent.isBlank()) {
                        return true;
                    }
                } else {
                    //If is a MSG type but don't have message_content, I consider it missing
                    return true;
                }
            }
        } 
        return false;
    }

    public static boolean haveErrorFields(Map<String, Object> invalidRecord) {
        String messageType = "";

        //Check if message_type have an error
        Object messageTypeObj = invalidRecord.get("message_type");
        //Checking if exist, if not is consider missing and not an error in this case
        if (messageTypeObj != null) {
            messageType = String.valueOf(messageTypeObj);
            if (!messageType.equals("CALL") && !messageType.equals("MSG")) {
                return true;
            }
        }

        //Check if timeStamp is valid
        Object timeStampObj = invalidRecord.get("timestamp");
        if (timeStampObj != null) {
            if (timeStampObj instanceof Integer) {
                Integer timestamp = (Integer) timeStampObj;
                if (timestamp <= 0) {
                    return true;
                }
            } else {
                //if timestamp is not a Integer, is an error
                return true;
            }
        }

        //Check if origin is valid
        Object originObj = invalidRecord.get("origin");
        if (originObj != null) {
            if (originObj instanceof Long) {
                String origin = String.valueOf(originObj);
                if (!origin.matches("^[1-9][0-9]{10,14}$")) {
                    return true;
                }
            } else {
                return true;
            }
        }

        //Check if destination is valid
        Object destinationObj = invalidRecord.get("destination");
        if (destinationObj != null) {
            if (destinationObj instanceof Long) {
                String destination = String.valueOf(destinationObj);
                if (!destination.matches("^[1-9][0-9]{10,14}$")) {
                    return true;
                }
            } else {
                return true;
            }
        }

        //Check specific type values
        if (messageType.equals("CALL")) {

            //Check duration
            Object durationObj = invalidRecord.get("duration");
            if (durationObj != null) {
               if (durationObj instanceof Integer) {
                Integer duration = (Integer) durationObj;
                if (duration <= 0) {
                    //If duration equals negative or 0 is an error in this case
                    return true;
                }
               } else {
                return true;
               }
            }

            //Check statusCode
            Object statusCodeObj = invalidRecord.get("status_code");
            if (statusCodeObj != null) {
                String statusCode = String.valueOf(statusCodeObj);
                if (!statusCode.equals("OK") && !statusCode.equals("KO")) {
                    return true;
                }
            }

        }

        if (messageType.equals("MSG")) {
            //Check message status
            Object messageStatusObj = invalidRecord.get("message_status");
            if (messageStatusObj != null) {
                String messageStatus = String.valueOf(messageStatusObj);
                if (!messageStatus.equals("DELIVERED") && !messageStatus.equals("SEEN")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<CountryOriginDestination> getCallsByOriginDestination(List<CountryOriginDestination> callsBCountryOriginDestinations, CallRecord callRecord) {
        //Get origin and destination country codes
        String originCountry = MSISDNCC.getCountryFromMSISDN(callRecord.getOrigin());
        String destinationCountry = MSISDNCC.getCountryFromMSISDN(callRecord.getDestination());

        //Check if the origin Country exists in the List, add one if exists
        boolean originCountryExists = false;
        for (CountryOriginDestination country : callsBCountryOriginDestinations) {
            if (country.getCountry().equals(originCountry)) {
                originCountryExists = true;
                //Add one if already exists
                country.setOriginCount(country.getOriginCount() + 1);
                break;
            }
        }

        //Create it if not exists
        if (!originCountryExists) {
            CountryOriginDestination newCountryFromOrigin = new CountryOriginDestination(originCountry, 1, 0);
            callsBCountryOriginDestinations.add(newCountryFromOrigin);
        }

        //Now the same with the destination Country
        boolean destinationCountryExistis = false;
        for (CountryOriginDestination country : callsBCountryOriginDestinations) {
            if (country.getCountry().equals(destinationCountry)) {
                destinationCountryExistis = true;
                country.setDestinationCount(country.getDestinationCount() + 1);
                break;
            }
        }

        if (!destinationCountryExistis) {
            CountryOriginDestination newCountryFromDestination = new CountryOriginDestination(destinationCountry, 0, 1);
            callsBCountryOriginDestinations.add(newCountryFromDestination);
        }

        return callsBCountryOriginDestinations;
    }

    public static Map<String, Long> getCallStatuses(Map<String, Long> callStatuses, CallRecord callRecord) {
        String callStatusCode = callRecord.getStatus_code().toString();

        if (callStatusCode.equals("OK")) {
            callStatuses.put("OK", callStatuses.get("OK") + 1);
        } else {
            callStatuses.put("KO", callStatuses.get("KO") + 1);
        }

        return callStatuses;
    }

    public static Map<String, Integer> getAvgCallDurationByCountry(List<CallRecord> callRecords) {
        Map<String, Integer> avgCallDurationByCountry = new HashMap<>();

        Map<String, Integer> durationPerCountry = new HashMap<>();
        Map<String, Integer> callsAmountByCountry = new HashMap<>();

        for (CallRecord callRecord : callRecords) {
            //I am taking origin country as reference por duration
            String originCountry = MSISDNCC.getCountryFromMSISDN(callRecord.getOrigin());

            durationPerCountry.put(originCountry, durationPerCountry.getOrDefault(originCountry, 0) + callRecord.getDuration());
            callsAmountByCountry.put(originCountry, callsAmountByCountry.getOrDefault(originCountry, 0) + 1);

        }

        for (String country : durationPerCountry.keySet()) {
            int durationSum = durationPerCountry.get(country);
            int amountCalls = callsAmountByCountry.get(country);

            int avg = durationSum / amountCalls;
            avgCallDurationByCountry.put(country, avg);
        }

        return avgCallDurationByCountry;
    }

}

