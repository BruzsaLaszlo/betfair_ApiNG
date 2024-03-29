package aping.util;

import aping.enums.ApiNgOperation;
import aping.enums.Endpoint;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Collectors;

import static aping.enums.Endpoint.NAVIGATION;
import static com.github.mizosoft.methanol.MoreBodyHandlers.decoding;
import static java.net.http.HttpResponse.BodyHandlers;
import static java.time.temporal.ChronoUnit.SECONDS;

@Log4j2
@Component
@RequiredArgsConstructor
public final class HttpUtil {

    @NonNull
    private final HttpClient httpClient;

    @NonNull
    private final ClientProperties clientProperties;


    public String sendPostRequest(ApiNgOperation operation, String jsonRequest, Endpoint endpoint) {

        String url = getUrl(endpoint, operation);
        HttpRequest request = getHttpRequest(jsonRequest, url);

        try {
            HttpResponse<String> response = httpClient.send(request, decoding(BodyHandlers.ofString()));
            log.debug(debugReponseHeaders(response));
            if (response.statusCode() != 200) throw new IllegalStateException(response.body());
            return response.body();
        } catch (InterruptedException | IOException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("http hiba", exception);
        }

    }

    private String getUrl(Endpoint endpoint, ApiNgOperation operation) {
        if (endpoint == NAVIGATION) return "https://api.betfair.com/exchange/betting/rest/v1/en/navigation/menu.json";
        String url = switch (endpoint) {
            case ACCOUNT -> "https://api.betfair.com/exchange/account/";
            case BETTING -> "https://api.betfair.com/exchange/betting/";
            case HEARTBEAT -> "https://api.betfair.com/exchange/heartbeat/";
            default -> throw new IllegalStateException("nincs ilyen Endpoint, hibás lesz az url");
        };
        return url + "rest/v1.0/" + operation.getName() + "/";
    }

    private HttpRequest getHttpRequest(String jsonRequest, String url) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .headers("Accept", "application/json")
                .headers("Accept-Charset", "UTF-8")
                .headers("X-Application", clientProperties.getApplicationKey())
                .headers("X-Authentication", clientProperties.getSessionToken())
                .headers("Accept-Encoding", "gzip,deflate")
//                .headers("Connection", "keep-alive")
                .timeout(Duration.of(10, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest == null ? "{}" : jsonRequest))
                .build();
        log.debug(debugRequestHeaders(url, request));
        log.info(infoRequestBody(jsonRequest));
        return request;
    }


    private String debugRequestHeaders(String url, HttpRequest request) {
        return String.format("""
                %n============================= HTTPUTIL =============================
                URL:
                    %s
                Request headers:
                %s
                """, url, headersToString(request.headers()));
    }

    private String infoRequestBody(String jsonRequest) {
        return "\n============================== REQUEST ==============================\n" + jsonRequest;
    }

    private String debugReponseHeaders(HttpResponse<String> response) {
        if (response.body().length() > 100_000)
            return "Response IS TOO BIG  { " + response.body().length() + " byte }";
        else
            return String.format("""
                    %n=========================== RESPONSE: %s ===========================
                    Response headers:
                    %s
                    %n=========================== RESPONSE BODY ==========================
                    %s
                    """, response.statusCode(), headersToString(response.headers()), response.body());
    }

    private String headersToString(HttpHeaders headers) {
        return headers.map().entrySet().stream()
                .map(entry -> "    " + entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}
