package com.cristian.callmessageprocessor.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cristian.callmessageprocessor.models.CallMessage;
import com.cristian.callmessageprocessor.models.MessageLog;
import com.cristian.callmessageprocessor.models.TextMessage;
import com.cristian.callmessageprocessor.utils.MessageValidators;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class JsonProcessingService {

    private final ObjectMapper objectMapper;

    public MessageLog processJson(String jsonContent) throws IOException {

        //Create a new MessageLog instance to store procesed messages
        MessageLog messageLog = new MessageLog();

        //Lists to store valid Call, Text and invalid messages
        List<CallMessage> callMessages = new ArrayList<>();
        List<TextMessage> textMessages = new ArrayList<>();
        List<Map<String, Object>> invalidMessages = new ArrayList<>();
        
        //Split the JSON content into individual lines
        String[] jsonLines = jsonContent.split("\n");
        log.info("Total registers readed: {}", jsonLines.length);
        int invalidCount = 0;
        //Loop through each line of JSON   
        for (int i = 0; i < jsonLines.length; i++) {

            boolean isValid = true;
            //Parse the JSON line into a JsonNode
            JsonNode node = objectMapper.readTree(jsonLines[i]);
            String messageType = node.path("message_type").asText();
            
            try {
                switch(messageType) {
                    case "CALL":
                        //Convert the JSON into a CallMessage object
                        CallMessage callMessage = objectMapper.convertValue(node, CallMessage.class);
                        //Check if the CallMessage is valid
                        if (!MessageValidators.isCallMessageValid(callMessage)) {
                            throw new IllegalArgumentException();
                        }
                        //Add the valid CallMessage to the list
                        callMessages.add(callMessage);
                        break;
                    case "MSG":
                        //Convert the JSON into a TextMessage object
                        TextMessage textMessage = objectMapper.convertValue(node, TextMessage.class);
                        //Check if the TextMessage is valid
                        if (!MessageValidators.isTextMessageValid(textMessage)) {
                            throw new IllegalArgumentException();
                        }
                        //Add the valid TextMessage to the list
                        textMessages.add(textMessage);
                        break;
                    default:
                        isValid = false;
                }
            } catch (IllegalArgumentException e) {
                isValid = false;
            }
            // If the message is not valid, convert it to a Map and add it to the invalidMessages list
            if (!isValid) {
                log.error("ROW NUMBER " + (i + 1) + " -> Invalid message");
                invalidCount++;
                TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                //Convert the JsonNode into a Map
                Map<String, Object> invalidMessage = objectMapper.convertValue(node, typeRef);
                invalidMessages.add(invalidMessage);
            }
        }
        if (invalidCount > 0) log.error(invalidCount + " messages are invalid");
        // Set the lists of valid and invalid messages in the MessageLog
        messageLog.setCallMessages(callMessages);
        messageLog.setTextMessages(textMessages);
        messageLog.setInvalidMessages(invalidMessages);

        return messageLog;
    }
}
