package aping.weather;

import aping.enums.CountryCodes;
import aping.util.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class OpenWeatherMap {

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public String getWeatherInBudapest() {
        return null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        URI uri = getUri(47.219915, 19.391116, CountryCodes.UNITED_KINGDOM);
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        String responseJson = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(responseJson);
        System.out.println(new JsonMapper().readValue(responseJson, Data.class));
    }


    public static URI getUri(double lat, double lon, CountryCodes countryCodes) {
        final String API_KEY = "6f23ea618d89b0c6f0b4e6ecd56220ed";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}&units={units}&lang={lang}"
                .replace("{lat}", String.valueOf(lat))
                .replace("{lon}", String.valueOf(lon))
                .replace("{units}", "metric")
                .replace("{API key}", API_KEY)
                .replace("{lang}", countryCodes.code);
        return URI.create(url);
    }

    public static URI getUri3(double lat, double lon, CountryCodes countryCodes, Exclude... excludes) {
        final String API_KEY = "6f23ea618d89b0c6f0b4e6ecd56220ed";
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={exclude}&appid={API key}&units={units}&lang={lang}"
                .replace("{lat}", String.valueOf(lat))
                .replace("{lon}", String.valueOf(lon))
                .replace("{exclude}", excludes == null ? "" : stream(excludes).map(Enum::toString).collect(joining(",")))
                .replace("{units}", "metric")
                .replace("{API key}", API_KEY)
                .replace("{lang}", countryCodes.code);
        return URI.create(url);
    }

}
