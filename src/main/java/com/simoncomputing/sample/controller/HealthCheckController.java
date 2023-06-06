package com.simoncomputing.sample.controller;

// Add these imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	
    // Create a new Logger for this class
    private Logger log = LoggerFactory.getLogger(HealthCheckController.class);
    
    @Value("${build.version}")
    private String buildVersion;
    
    @Value("${build.timestamp}")
    private String buildTime;
    
    @Value("${build.number}")
    private String buildNumber;
    
    @GetMapping("/healthcheck")
    public String getHealthCheck() {
    	
        String result = "[Sample Service]<br>Build version: " + buildVersion + "<br>Build time: " + buildTime + "<br>Build number: " + buildNumber;

        // Create a log message
        log.debug(result);      
        
        return result;
    }
}