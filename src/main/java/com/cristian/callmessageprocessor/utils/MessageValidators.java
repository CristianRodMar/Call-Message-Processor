package com.cristian.callmessageprocessor.utils;

import com.cristian.callmessageprocessor.models.CallMessage;
import com.cristian.callmessageprocessor.models.TextMessage;

public class MessageValidators {
    
    public static boolean isCallMessageValid(CallMessage callMessage) {
        boolean isValid = true;

        if (callMessage.getTimestamp() <= 0) {
            isValid = false;
        }

        return isValid;
    }

    public static boolean isTextMessageValid(TextMessage textMessage) {
        return true; 
    }
}
