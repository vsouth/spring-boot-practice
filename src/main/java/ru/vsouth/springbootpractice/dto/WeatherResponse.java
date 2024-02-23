package ru.vsouth.springbootpractice.dto;

public class WeatherResponse {
    private final Location location;
    private final double avgTempMin;
    private final double avgTempHour;
    private final double avgTempDay;



    public WeatherResponse(Location location, double avgTempMin, double avgTempHour, double avgTempDay) {
        this.location = location;
        this.avgTempMin = avgTempMin;
        this.avgTempHour = avgTempHour;
        this.avgTempDay = avgTempDay;
    }

    public Location getLocation() {
        return location;
    }

    public double getAvgTempMin() {
        return avgTempMin;
    }

    public double getAvgTempHour() {
        return avgTempHour;
    }

    public double getAvgTempDay() {
        return avgTempDay;
    }
}
