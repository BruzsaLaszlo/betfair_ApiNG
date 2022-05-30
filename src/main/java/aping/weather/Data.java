package aping.weather;

import java.util.List;

public record Data(

        Coord coord,
        List<Weather> weather,
        String base,
        Main main,
        int visibility,
        Wind wind,
        Clouds clouds,
        int dt,
        Sys sys,
        int timezone,
        int id,
        String name,
        int cod

) {


    record Coord(
            double lon,
            double lat
    ) {
    }

    public record Weather(
            int id,
            String main,
            String description,
            String icon
    ) {
    }

    public record Main(
            double temp,
            double feels_like,
            double temp_min,
            double temp_max,
            int pressure,
            int humidity,
            int sea_level,
            int grnd_level
    ) {
    }

    public record Wind(
            double speed,
            int deg,
            double gust
    ) {
    }

    public record Clouds(
            int all
    ) {
    }

    public record Sys(
            int type,
            int id,
            double message,
            String country,
            int sunrise,
            int sunset
    ) {
    }

}

