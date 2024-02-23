package ru.vsouth.springbootpractice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vsouth.springbootpractice.dto.Location;
import ru.vsouth.springbootpractice.dto.WeatherResponse;

@Component
public class WeatherResponseMapper {
    private final ObjectMapper jsonParser = new ObjectMapper();
    public WeatherResponse mapStringToWeatherResponse(String str) throws JsonProcessingException {
        JsonNode jsonNode = jsonParser.readTree(str);
        return new WeatherResponse(
                getLocation(jsonNode),
                getAvgTemperatures(jsonNode, "minutely", "temperature"),
                getAvgTemperatures(jsonNode, "hourly", "temperature"),
                getAvgTemperatures(jsonNode, "daily", "temperatureAvg")
        );
    }

    private double getAvgTemperatures(JsonNode jsonNode, String range, String nodeName) {
        return jsonNode.get("timelines").get(range).findValues(nodeName)
                .stream()
                .mapToDouble(x -> Double.parseDouble(x.asText()))
                .average()
                .getAsDouble();
    }

    private Location getLocation(JsonNode jsonNode) {
        return new Location(
                jsonNode.get("location").findValue("lat").asText(),
                jsonNode.get("location").findValue("lon").asText());
    }
}
