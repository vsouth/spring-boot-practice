package ru.vsouth.springbootpractice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.vsouth.springbootpractice.dto.WeatherResponse;
import ru.vsouth.springbootpractice.service.WeatherService;

import static ru.vsouth.springbootpractice.service.WeatherService.getWeatherResponse;

@RestController
public class InfoController {
    private final WeatherService weatherService;
    private final String HTTPS_API_TOMORROW = "https://api.tomorrow.io/v4/weather/forecast";
    private final String LOCATION = "42.3478,-71.0466";
    private final String APIKEY = "sMBL3CZBNa2w0zwC8Rnj8JXHRB7ETCrQ";

    public InfoController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/get_weather")
    public WeatherResponse getWeather() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForEntity(HTTPS_API_TOMORROW + "?location=" + LOCATION + "&apikey=" + APIKEY, String.class).getBody();
        WeatherResponse weatherResponse = getWeatherResponse(response);
        return weatherResponse;
    }
}
