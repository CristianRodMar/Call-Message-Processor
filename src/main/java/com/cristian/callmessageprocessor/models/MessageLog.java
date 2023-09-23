package com.cristian.callmessageprocessor.models;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A complete log file based on a specific date")
public class MessageLog {
    
    @Schema(description = "List of all Call message types")
    private List<CallMessage> callMessages;

    @Schema(description = "List of all Text message types")
    private List<TextMessage> textMessages;

    @Schema(description = "List of all invalid messages")
    private List<Map<String, Object>> invalidMessages;

}
