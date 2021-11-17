package bruzsal.betfair.util;

import bruzsal.betfair.enums.Endpoint;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HTTPUTIL2 {


    private static final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private static final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";
    private static final String HTTP_HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static final Properties prop = new Properties();
    private static boolean DEBUG;

    static {
        System.setProperty("jdk.httpclient.allowRestrictedHeaders", "Connection");
        try (InputStream in = HttpUtil.class.getResourceAsStream("/apingdemo.properties")) {

            prop.load(in);
            DEBUG = Boolean.parseBoolean(prop.getProperty("DEBUG"));
            prop.setProperty("SESSION_TOKEN", SessionTokenGetter.getAndSetSessionTokenToProperety());

        } catch (UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e);
        }
    }


    public static String sendPostRequest(String operation, String jsonRequest, Endpoint endpoint) throws IOException, URISyntaxException, InterruptedException {

        String url;
        switch (endpoint) {
            case ACCOUNT -> url = prop.getProperty("ACCOUNT_APING_URL");
            case BETTING -> url = prop.getProperty("SPORT_APING_URL");
            case HEARTBEAT -> url = prop.getProperty("HEARTBEAT_URL");
            default -> url = "";
        }
        url += prop.getProperty("RESCRIPT_SUFFIX") + operation + "/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers(HTTP_HEADER_CONTENT_TYPE, prop.getProperty("APPLICATION_JSON"))
                .headers(HTTP_HEADER_ACCEPT, prop.getProperty("APPLICATION_JSON"))
                .headers(HTTP_HEADER_ACCEPT_CHARSET, CHARSET_UTF8)
                .headers(HTTP_HEADER_X_APPLICATION, prop.getProperty("APPLICATION_KEY"))
                .headers(HTTP_HEADER_X_AUTHENTICATION, prop.getProperty("SESSION_TOKEN"))
                .headers(HTTP_HEADER_ACCEPT_ENCODING, "gzip,deflate")
                .headers("Connection", "keep-alive")
                .timeout(Duration.of(10, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest == null ? "{}" : jsonRequest))
                .build();

        if (jsonRequest != null) {
        }

        if (DEBUG) {
            System.out.println("Request headers: " + request.headers());
            System.out.println("URL: " + url);
            System.out.println("jsonRequest: " + jsonRequest);
        }

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));

        if (DEBUG)
            System.out.println(
                    "Response version: " + response.version().name() + "\n" +
                            "Response: " + response.body());


        if (response.statusCode() != 200) {
            throw new HttpResponseException(response.statusCode(), response.body());
        }

        return response.body();
    }
}
