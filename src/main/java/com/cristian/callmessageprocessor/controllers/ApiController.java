package com.cristian.callmessageprocessor.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
        @ApiResponse(responseCode = "404", description = "The log with the given date was not found", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "400", description = "The format date is not correct", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{date}")
    public ResponseEntity<Object> processJsonForDate(@Parameter(description = "Search log by date format YYYYMMDD") @PathVariable String date) throws IOException {
        try {
            String jsonContent = downloadService.downloadJsonFile(date);
            MessageLog messageLog = processingService.processJson(jsonContent);
            return ResponseEntity.ok(messageLog);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"code\": 400, \"message\": \"Invalid date format. Use YYYYMMDD\"}");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": 404, \"message\": \"JSON not found for date " + date + "\"}");
        }

    }

    //@GetMapping("/{date}/metrics") metrics for a json file, if not exists in memory, process it

    //@GetMapping("/{date}/kpis") kpis for a json file, , if not exists in memory, process it

    //@GetMapping("/metrics") metrics for all procesed json files

    //@GetMapping("/kpis") kpis for all procesed json files

    //("/reset") reset all the saved values

}
