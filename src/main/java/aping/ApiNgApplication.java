package aping;

import aping.util.ClientProperties;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;

@SpringBootApplication
@EnableConfigurationProperties(ClientProperties.class)
public class ApiNgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiNgApplication.class, args);
    }

    @Bean
    public HttpClient createHttpClient() {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Connection");
        return HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();
    }

    @Bean
    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
