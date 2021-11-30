package bruzsal.betfair.util;

import bruzsal.betfair.enums.CountryCodes;
import bruzsal.betfair.enums.Endpoint;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.xml.Log4jEntityResolver;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Properties;

public final class HttpUtil {

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
        try (InputStream in = HttpUtil.class.getResourceAsStream("/apingdemo.properties")) {
            prop.load(in);
            DEBUG = Boolean.parseBoolean(prop.getProperty("DEBUG"));
            SessionTokenGetter.getAndSetSessionTokenToProperety();

        } catch (UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e);
        }
    }

    static {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.http");
        root.setLevel(ch.qos.logback.classic.Level.INFO);
    }

    public static String sendPostRequest(String operation, String jsonRequest, Endpoint endpoint) throws IOException {

        String url = prop.getProperty("RESCRIPT_SUFFIX") + operation + "/";
        switch (endpoint) {
            case ACCOUNT -> url = prop.getProperty("ACCOUNT_APING_URL") + url;
            case BETTING -> url = prop.getProperty("SPORT_APING_URL") + url;
            case HEARTBEAT -> url = prop.getProperty("HEARTBEAT_URL") + url;
            case NAVIGATION -> url = prop.getProperty(("NAVIGATION_URL"));
            default -> url = "";
        }

        HttpPost post = new HttpPost(url);
        {
            post.setHeader(HTTP_HEADER_CONTENT_TYPE, prop.getProperty("APPLICATION_JSON"));
            post.setHeader(HTTP_HEADER_ACCEPT, prop.getProperty("APPLICATION_JSON"));
            post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, CHARSET_UTF8);
            post.setHeader(HTTP_HEADER_X_APPLICATION, prop.getProperty("APPLICATION_KEY"));
            post.setHeader(HTTP_HEADER_X_AUTHENTICATION, prop.getProperty("SESSION_TOKEN"));
            post.setHeader(HTTP_HEADER_ACCEPT_ENCODING, "gzip,deflate");
            post.setHeader("Connection", "keep-alive");
            if (jsonRequest != null)
                post.setEntity(new StringEntity(jsonRequest, CHARSET_UTF8));
        }

        if (DEBUG) {
            System.out.println("Request headers: " + Arrays.toString(post.getAllHeaders()));
            System.out.println("URL: " + post.getURI().toString());
            System.out.println("jsonRequest: " + jsonRequest);
        }

        return HttpClientBuilder.create().build().execute(post, (HttpUtil::handleResponse));

    }

    private static String handleResponse(HttpResponse httpResponse) throws IOException {

        HttpEntity entity = httpResponse.getEntity();
        String entityString = entity == null ? "null" : EntityUtils.toString(entity, CHARSET_UTF8);

        if (DEBUG)
            if (entityString.length() > 100_000)
                System.out.println("Response IS TOO BIG  { " + entityString.length() + " byte }");
            else
                System.out.println("Response: " + entityString);

        var statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != 200) throw new IllegalStateException(entityString);

        return entityString;

    }


    private HttpUtil() {
    }


}
