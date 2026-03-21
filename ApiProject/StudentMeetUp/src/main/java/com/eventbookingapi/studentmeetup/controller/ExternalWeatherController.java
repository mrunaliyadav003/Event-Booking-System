package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.model.WeatherInfo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("external")
public class ExternalWeatherController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(
            @RequestParam String location,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (location == null || location.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Location is required"));
        }

        LocalDate requestedDate = date;

        String geoUrl = UriComponentsBuilder
                .fromUriString("https://geocoding-api.open-meteo.com/v1/search")
                .queryParam("name", location)
                .queryParam("count", 1)
                .queryParam("language", "en")
                .queryParam("format", "json")
                .toUriString();

        Map<String, Object> geoResponse;
        try {
            geoResponse = restTemplate.getForObject(geoUrl, Map.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Failed to reach geocoding service"));
        }
        List<Map<String, Object>> results = extractResults(geoResponse);
        if (results == null || results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Location not found"));
        }

        Map<String, Object> first = results.get(0);
        double latitude = toDouble(first.get("latitude"));
        double longitude = toDouble(first.get("longitude"));
        String resolvedName = first.get("name") != null ? first.get("name").toString() : location;
        String country = first.get("country") != null ? first.get("country").toString() : "";

        String forecastUrl = UriComponentsBuilder
                .fromUriString("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("daily", "temperature_2m_max,temperature_2m_min,precipitation_probability_mean")
                .queryParam("timezone", "auto")
                .queryParam("forecast_days", 16)
                .toUriString();

        Map<String, Object> forecastResponse;
        try {
            forecastResponse = restTemplate.getForObject(forecastUrl, Map.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Failed to reach weather service"));
        }
        Map<String, Object> daily = forecastResponse == null ? null : castMap(forecastResponse.get("daily"));

        List<String> days = extractStringList(daily, "time");
        if (days == null || days.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Weather data unavailable"));
        }

        int index = 0;
        String resolvedDate = days.get(0);
        if (requestedDate != null) {
            String requestedDateText = requestedDate.toString();
            index = days.indexOf(requestedDateText);
            if (index < 0) {
                String range = days.get(0) + " to " + days.get(days.size() - 1);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Forecast available from " + range));
            }
            resolvedDate = requestedDateText;
        }

        Double tempMax = extractNumberAtIndex(daily, "temperature_2m_max", index);
        Double tempMin = extractNumberAtIndex(daily, "temperature_2m_min", index);
        Double precipitation = extractNumberAtIndex(daily, "precipitation_probability_mean", index);

        WeatherInfo info = new WeatherInfo(
                resolvedName,
                country,
                resolvedDate,
                latitude,
                longitude,
                tempMax,
                tempMin,
                precipitation
        );

        return ResponseEntity.ok(info);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> extractResults(Map<String, Object> response) {
        if (response == null) {
            return null;
        }
        return (List<Map<String, Object>>) response.get("results");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return value instanceof Map ? (Map<String, Object>) value : null;
    }

    private List<String> extractStringList(Map<String, Object> daily, String key) {
        if (daily == null || !daily.containsKey(key)) {
            return null;
        }
        Object values = daily.get(key);
        if (!(values instanceof List)) {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) values;
        return list.stream().map(value -> value == null ? "" : value.toString()).toList();
    }

    private Double extractNumberAtIndex(Map<String, Object> daily, String key, int index) {
        if (daily == null || !daily.containsKey(key)) {
            return null;
        }
        Object values = daily.get(key);
        if (!(values instanceof List)) {
            return null;
        }
        List<?> list = (List<?>) values;
        if (list.isEmpty() || index < 0 || index >= list.size() || list.get(index) == null) {
            return null;
        }
        return toDouble(list.get(index));
    }

    private double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return Double.parseDouble(value.toString());
    }
}
