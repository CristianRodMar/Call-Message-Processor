package com.cristian.callmessageprocessor;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = CallMessageProcessorApplication.class, webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CallMessageProcessorApplication.class, loader = SpringBootContextLoader.class)
public class CucumberSpringConfiguration {
    
}
