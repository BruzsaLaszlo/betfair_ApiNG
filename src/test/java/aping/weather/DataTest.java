package aping.weather;

import aping.util.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DataTest {

    @Test
    void parseJson() throws IOException {
        String json = Files.readString(Path.of("src/test/resources/aping/weather/openweather.json"));
        Data data = new JsonMapper().readValue(json, Data.class);
        assertThat(data.base()).isNotBlank();
    }
}