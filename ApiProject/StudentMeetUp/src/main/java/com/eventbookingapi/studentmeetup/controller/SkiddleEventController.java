package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.model.SkiddleData;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

@RestController
@CrossOrigin
@RequestMapping(path="skiddleEventData")
public class SkiddleEventController {
    //private  final SkiddleEventService skiddleEventService;
    //public SkiddleEventController(SkiddleEventService skiddleEventService) {
    //    this.skiddleEventService = skiddleEventService;
   // }

    @GetMapping("/search")
    public String searchEvents( @RequestParam(defaultValue = "52.950001") double lat, @RequestParam(defaultValue = "-1.150000") double lon,@RequestParam(defaultValue = "5") int radius)
    {
        String YOUR_API_KEY="10b7a4ff78df7f0759306016376c3a34";
        String url = "https://www.skiddle.com/api/v1/events/search/?api_key="+YOUR_API_KEY+"&latitude="+lat+"&longitude="+lon+"&radius="+radius;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url,String.class);
        return result;
    }


}
