package ru.vsouth.springbootpractice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import ru.vsouth.springbootpractice.dto.WeatherResponse;
import ru.vsouth.springbootpractice.mapper.WeatherResponseMapper;

@Service
public class WeatherService {
    private static final String HTTPS_API_TOMORROW = "https://api.tomorrow.io/v4/weather/forecast";
    private static final String LOCATION = "42.3478,-71.0466";
    private static final String APIKEY = "sMBL3CZBNa2w0zwC8Rnj8JXHRB7ETCrQ";
    private static final WeatherResponseMapper weatherResponseMapper = new WeatherResponseMapper();


    public static WeatherResponse getWeatherResponse(String str) throws JsonProcessingException {
        return weatherResponseMapper.mapStringToWeatherResponse(str);
    }

}
