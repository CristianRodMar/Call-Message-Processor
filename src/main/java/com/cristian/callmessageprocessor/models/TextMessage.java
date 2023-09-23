package com.cristian.callmessageprocessor.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Text type message")
public class TextMessage {
    
    @Schema(description = "The timestamp of the message")
    private long timestamp;

    @Schema(description = "Mobile identifier of the origin mobile (MSISDN)")
    private String origin;

    @Schema(description = "Mobile identifier of the destination mobile (MSISDN)")
    private String destination;

    @Schema(description = "Content of the message")
    private String message_content;

    @Schema(description = "Status of the message. Two values are valid: {DELIVERED|SEEN}")
    private TextStatusCode message_status;
    
}
