package com.cristian.callmessageprocessor.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.callmessageprocessor.models.MessageLog;
import com.cristian.callmessageprocessor.services.JsonDownloadService;
import com.cristian.callmessageprocessor.services.JsonProcessingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "API Controller", description = "API management controller")
@RestController
@RequestMapping("/")
public class ApiController {

    private final JsonDownloadService downloadService;
    private final JsonProcessingService processingService;

    @Autowired
    public ApiController(JsonDownloadService downloadService, JsonProcessingService processingService) {
        this.downloadService = downloadService;
        this.processingService = processingService;
    }

    @Operation(summary = "Retrieve a MessageLog by date", 
        description = "Get a MessageLog object by specifying its date. The response is a JSON with two arrays with all CallMessages and TextMessages of the log")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation =  MessageLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "The log with the given date was not found", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{date}")
    public MessageLog processJsonForDate(@Parameter(description = "Search log by date format YYYYMMDD") @PathVariable String date) throws IOException {
        String jsonContent = downloadService.downloadJsonFile(date);
        return processingService.processJson(jsonContent);
    }

    //@GetMapping("/{date}/metrics") metrics for a json file

    //@GetMapping("/{date}/kpis") kpis for a json file

    //@GetMapping("/metrics") metrics for all procesed json files

    //@GetMapping("/kpis") kpis for all procesed json files

    //("/reset") reset all the saved values

}
