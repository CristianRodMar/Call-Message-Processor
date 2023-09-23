package com.cristian.callmessageprocessor.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Call type message")
public class CallMessage {
    
    @Schema(description = "The timestamp of the message")
    private long timestamp;

    @Schema(description = "Mobile identifier of the origin mobile (MSISDN)")
    private String origin;

    @Schema(description = "Mobile identifier of the destination mobile (MSISDN)")
    private String destination;

    @Schema(description = "Call duration")
    private int duration;

    @Schema(description = "Status code of the call. Two values are valid: {OK|KO}")
    private CallStatusCode status_code;

    @Schema(description = "Status description of the call")
    private String status_description;

}
