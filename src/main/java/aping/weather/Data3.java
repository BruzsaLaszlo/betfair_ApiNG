package aping.weather;

import java.util.List;

public record Data3(

        double lat,
        double lon,
        String timezone,
        int timezone_offset,
        Current current,
        List<Minutely> minutely,
        List<Hourly> hourly,
        List<Daily> daily,
        List<Alert> alerts) {

    record Alert(
            String sender_name,
            String event,
            int start,
            int end,
            String description,
            List<Object> tags
    ) {
    }

    record Current(
            int dt,
            int sunrise,
            int sunset,
            double temp,
            double feels_like,
            int pressure,
            int humidity,
            double dew_point,
            double uvi,
            int clouds,
            int visibility,
            double wind_speed,
            int wind_deg,
            double wind_gust,
            List<Weather> weather
    ) {
    }

    record Daily(
            int dt,
            int sunrise,
            int sunset,
            int moonrise,
            int moonset,
            double moon_phase,
            Temp temp,
            FeelsLike feels_like,
            int pressure,
            int humidity,
            double dew_point,
            double wind_speed,
            int wind_deg,
            double wind_gust,
            List<Weather> weather,
            int clouds,
            double pop,
            double rain,
            double uvi
    ) {
    }

    record FeelsLike(
            double day,
            double night,
            double eve,
            double morn
    ) {
    }

    record Hourly(
            int dt,
            double temp,
            double feels_like,
            int pressure,
            int humidity,
            double dew_point,
            double uvi,
            int clouds,
            int visibility,
            double wind_speed,
            int wind_deg,
            double wind_gust,
            List<Weather> weather,
            double pop
    ) {
    }

    record Minutely(
            int dt,
            int precipitation
    ) {
    }

    record Temp(
            double day,
            double min,
            double max,
            double night,
            double eve,
            double morn
    ) {
    }

    record Weather(
            int id,
            String main,
            String description,
            String icon
    ) {
    }
}

