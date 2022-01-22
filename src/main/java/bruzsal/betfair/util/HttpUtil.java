package bruzsal.betfair.util;

import bruzsal.betfair.enums.ApiNgOperation;
import bruzsal.betfair.enums.Endpoint;
import com.github.mizosoft.methanol.MoreBodyHandlers;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Collectors;

import static bruzsal.betfair.enums.Endpoint.NAVIGATION;
import static bruzsal.betfair.util.Properties.*;
import static java.time.temporal.ChronoUnit.SECONDS;

public final class HttpUtil {

    private HttpUtil() {
    }

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
            .build();

    static {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Connection");
    }


    public static String sendPostRequest(ApiNgOperation operation, String jsonRequest, Endpoint endpoint) {

        String url = getUrl(endpoint, operation);

        HttpRequest request = getHttpRequest(jsonRequest, url);

        if (debug) System.out.println(getDebugMessage(jsonRequest, url, request));

        try {

            HttpResponse<String> response = HTTP_CLIENT
                    .send(request, MoreBodyHandlers.decoding(HttpResponse.BodyHandlers.ofString()));

            if (debug) System.out.println(getDebugMessage(response));

            if (response.statusCode() != 200) throw new IllegalStateException(response.body());

            return response.body();

        } catch (InterruptedException | IOException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("http hiba", exception);
        }

    }

    private static String getUrl(Endpoint endpoint, ApiNgOperation operation) {
        if (endpoint == NAVIGATION) return "https://api.betfair.com/exchange/betting/rest/v1/en/navigation/menu.json";
        String url = switch (endpoint) {
            case ACCOUNT -> "https://api.betfair.com/exchange/account/";
            case BETTING -> "https://api.betfair.com/exchange/betting/";
            case HEARTBEAT -> "https://api.betfair.com/exchange/heartbeat/";
            default -> throw new IllegalStateException("nincs ilyen Endpoint, hibás lesz az url");
        };
        return url + "rest/v1.0/" + operation.getName() + "/";
    }

    private static HttpRequest getHttpRequest(String jsonRequest, String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .headers("Accept", "application/json")
                .headers("Accept-Charset", "UTF-8")
                .headers("X-Application", APPLICATION_KEY.value())
                .headers("X-Authentication", SESSION_TOKEN.value())
                .headers("Accept-Encoding", "gzip,deflate")
//                .headers("Connection", "keep-alive")
                .timeout(Duration.of(10, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest == null ? "{}" : jsonRequest))
                .build();
    }


    private static String getDebugMessage(String jsonRequest, String url, HttpRequest request) {
        return String.format("""
                %n============================= HTTPUTIL =============================
                URL:
                    %s
                Request headers:
                %s
                ============================== REQUEST ==============================
                %s
                """, url, headersToString(request.headers()), jsonRequest);
    }

    private static String getDebugMessage(HttpResponse<String> response) {
        if (response.body().length() > 100_000)
            return "Response IS TOO BIG  { " + response.body().length() + " byte }";
        else
            return String.format("""
                    =========================== RESPONSE: %s ===========================
                    Response headers:
                    %s
                    %n=========================== RESPONSE BODY ==========================
                    %s
                    =========================== HTTPUTIL END ===========================
                    """, response.statusCode(), headersToString(response.headers()), response.body());
    }

    @NotNull
    private static String headersToString(HttpHeaders headers) {
        return headers.map().entrySet().stream()
                .map(entry -> "    " + entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}
