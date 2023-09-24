package com.cristian.callmessageprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryOriginDestination {
    
    private String country;
    private long originCount;
    private long destinationCount;
    
}
