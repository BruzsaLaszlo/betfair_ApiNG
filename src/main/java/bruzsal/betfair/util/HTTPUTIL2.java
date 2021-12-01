package bruzsal.betfair.util;

import bruzsal.betfair.enums.ApiNgOperation;
import bruzsal.betfair.enums.Endpoint;
import com.github.mizosoft.methanol.MoreBodyHandlers;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HTTPUTIL2 {


    public static final Properties prop = new Properties();
    public static boolean debug;

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
            .build();

    static {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Connection");
        try (InputStream in = HTTPUTIL2.class.getResourceAsStream("/apingdemo.properties")) {

            prop.load(in);
            debug = Boolean.parseBoolean(prop.getProperty("DEBUG"));
            prop.setProperty("SESSION_TOKEN", SessionTokenGetter.get());

        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e);
        }
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
            throw new IllegalStateException("http hiba", exception);
        }

    }

    private static String getUrl(Endpoint endpoint, ApiNgOperation operation) {
        String url;
        switch (endpoint) {
            case ACCOUNT -> url = "https://api.betfair.com/exchange/account/";
            case BETTING -> url = "https://api.betfair.com/exchange/betting/";
            case HEARTBEAT -> url = "https://api.betfair.com/exchange/heartbeat/";
            case NAVIGATION -> {
                return "https://api.betfair.com/exchange/betting/rest/v1/en/navigation/menu.json";
            }
            default -> throw new IllegalStateException("nincs ilyen Endpoint, hibás lesz az url");
        }
        return url + "rest/v1.0/" + operation.getOperationName() + "/";
    }

    private static HttpRequest getHttpRequest(String jsonRequest, String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .headers("Accept", "application/json")
                .headers("Accept-Charset", "UTF-8")
                .headers("X-Application", prop.getProperty("APPLICATION_KEY"))
                .headers("X-Authentication", prop.getProperty("SESSION_TOKEN"))
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
