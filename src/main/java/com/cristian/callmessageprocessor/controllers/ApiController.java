package com.cristian.callmessageprocessor.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.cristian.callmessageprocessor.dto.KPIs;
import com.cristian.callmessageprocessor.dto.Metrics;
import com.cristian.callmessageprocessor.models.Records;
import com.cristian.callmessageprocessor.services.JsonDownloadService;
import com.cristian.callmessageprocessor.services.JsonProcessingService;
import com.cristian.callmessageprocessor.services.MetricsAndKPIsService;

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

    //Maps to store metrics and kpis
    private final Map<String, Metrics> metricsMap = new ConcurrentHashMap<>();
    private final Map<String, KPIs> kpisMap = new ConcurrentHashMap<>();

    private final JsonDownloadService downloadService;
    private final JsonProcessingService processingService;
    private final MetricsAndKPIsService metricsAndKPIsService;

    @Autowired
    public ApiController(JsonDownloadService downloadService, JsonProcessingService processingService, MetricsAndKPIsService metricsAndKPIsService) {
        this.downloadService = downloadService;
        this.processingService = processingService;
        this.metricsAndKPIsService = metricsAndKPIsService;
    }

    @Operation(summary = "Retrieve a records of messages by date", 
        description = "Get a Records object by specifying its date. The response is a JSON with two arrays with all CallMessages and TextMessages of the log")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation =  Records.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "The log with the given date was not found", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "400", description = "The format date is not correct", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{date}")
    public ResponseEntity<Object> processJsonForDate(@Parameter(description = "Search file by date format YYYYMMDD") @PathVariable String date) throws IOException {
        try {
            //Download and process the Json
            String jsonContent = downloadService.downloadJsonFile(date);

            long startTime = System.currentTimeMillis();
            Records records = processingService.processJson(jsonContent);
            long endTime = System.currentTimeMillis();

            //Calculate metrics and kpis for the actual date and store them in maps
            Metrics metrics = metricsAndKPIsService.calculateMetrics(records);
            KPIs kpis = metricsAndKPIsService.calculateKPIs(records);
            kpis.getProcessDurations().put(date, (endTime - startTime));         

            metricsMap.put(date, metrics);
            kpisMap.put(date, kpis);

            return ResponseEntity.ok(records);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"code\": 400, \"message\": \"Invalid date format. Use YYYYMMDD\"}");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": 404, \"message\": \"JSON not found for date " + date + "\"}");
        }

    }

    @GetMapping("/{date}/metrics") //metrics for a json file, if not exists in memory, process it
    public ResponseEntity<Object> getDateMetrics(@Parameter(description = "Search file by date format YYYYMMDD") @PathVariable String date) throws IOException {
        try {
            Metrics metrics = new Metrics();
            //Check if the metrics of the file are already saved
            if (metricsMap.containsKey(date)) {
                metrics = metricsMap.get(date);
                return ResponseEntity.ok(metrics);
            } else {
                //Download and calculate the file if not
                String jsonContent = downloadService.downloadJsonFile(date);
                long startTime = System.currentTimeMillis();
                Records records = processingService.processJson(jsonContent);
                long endTime = System.currentTimeMillis();
                metrics = metricsAndKPIsService.calculateMetrics(records);
                KPIs kpis = metricsAndKPIsService.calculateKPIs(records);
                kpis.getProcessDurations().put(date, (endTime - startTime));
                metricsMap.put(date, metrics);
                kpisMap.put(date, kpis);
                return ResponseEntity.ok(metrics);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"code\": 400, \"message\": \"Invalid date format. Use YYYYMMDD\"}");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": 404, \"message\": \"JSON not found for date " + date + "\"}");
        }
    }
    //@GetMapping("/{date}/kpis") kpis for a json file, , if not exists in memory, process it

    //@GetMapping("/metrics") metrics for all procesed json files

    //@GetMapping("/kpis") kpis for all procesed json files

    //("/reset") reset all the saved values

}
