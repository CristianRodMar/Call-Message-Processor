package com.cristian.callmessageprocessor.utils;

import com.cristian.callmessageprocessor.models.CallMessage;
import com.cristian.callmessageprocessor.models.TextMessage;

public class MessageValidators {
    
    public static boolean isCallMessageValid(CallMessage callMessage) {
        boolean isValid = true;

        if (callMessage.getTimestamp() <= 0) isValid = false;

        if (callMessage.getDuration() <= 0) isValid = false;

        if (!callMessage.getDestination().matches("^[1-9][0-9]{10,14}$")) isValid = false;

        if (!callMessage.getOrigin().matches("^[1-9][0-9]{10,14}$")) isValid = false;

        return isValid;
    }

    public static boolean isTextMessageValid(TextMessage textMessage) {
        boolean isValid = true;

        if(textMessage.getTimestamp() <= 0) isValid = false;

        if(textMessage.getMessage_content() == null || textMessage.getMessage_content().length() == 0 || textMessage.getMessage_content().isBlank())
            isValid = false;

        if (!textMessage.getDestination().matches("^[1-9][0-9]{10,14}$")) isValid = false;

        if (!textMessage.getOrigin().matches("^[1-9][0-9]{10,14}$")) isValid = false;

        return isValid; 
    }
}
