package com.eventbookingapi.studentmeetup.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping(path="skiddleEventData")
public class SkiddleEventController {
    @Value("${external.skiddle.api-key:}")
    private String skiddleApiKey;
    //private  final SkiddleEventService skiddleEventService;
    //public SkiddleEventController(SkiddleEventService skiddleEventService) {
    //    this.skiddleEventService = skiddleEventService;
   // }

    @GetMapping("/search")
    public ResponseEntity<String> searchEvents( @RequestParam(defaultValue = "52.950001") double lat,
                               @RequestParam(defaultValue = "-1.150000") double lon,
                               @RequestParam(defaultValue = "5") int radius,
                               @RequestParam(defaultValue = "false") boolean studentOnly)
    {
        if (skiddleApiKey == null || skiddleApiKey.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"Skiddle API key not configured\"}");
        }

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://www.skiddle.com/api/v1/events/search/")
                .queryParam("api_key", skiddleApiKey)
                .queryParam("latitude", lat)
                .queryParam("longitude", lon)
                .queryParam("radius", radius);

        // Add student-specific keyword filter if requested
        if (studentOnly) {
            builder.queryParam("keyword", "student");
        }

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(builder.toUriString(), String.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }


}
