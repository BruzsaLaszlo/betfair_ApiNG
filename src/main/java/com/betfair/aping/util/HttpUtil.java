package com.betfair.aping.util;


import com.betfair.aping.enums.Endpoint;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
            prop.setProperty("SESSION_TOKEN", SessionTokenGetter.getSessionToken());

        } catch (UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e);
        }
    }


    public static String sendPostRequest(String operation, String jsonRequest, Endpoint endpoint) throws IOException {

        String url;
        if (endpoint == Endpoint.ACCOUNT)
            url = prop.getProperty("ACCOUNT_APING_URL") + prop.getProperty("RESCRIPT_SUFFIX") + operation + "/";
        else
            url = prop.getProperty("SPORT_APING_URL") + prop.getProperty("RESCRIPT_SUFFIX") + operation + "/";

        HttpPost post = new HttpPost(url);
        post.setHeader(HTTP_HEADER_CONTENT_TYPE, prop.getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT, prop.getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, CHARSET_UTF8);
        post.setHeader(HTTP_HEADER_X_APPLICATION, prop.getProperty("APPLICATION_KEY"));
        post.setHeader(HTTP_HEADER_X_AUTHENTICATION, prop.getProperty("SESSION_TOKEN"));
        post.setHeader(HTTP_HEADER_ACCEPT_ENCODING, prop.getProperty(HTTP_HEADER_ACCEPT_ENCODING));
        post.setHeader("Accept-Encoding", "gzip,deflate");
        post.setHeader("Connection", "keep-alive");
        if (jsonRequest != null)
            post.setEntity(new StringEntity(jsonRequest, CHARSET_UTF8));

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
            System.out.println("Response: " + entityString);

        StatusLine statusLine = httpResponse.getStatusLine();
        if (statusLine.getStatusCode() != 200) {
            throw new HttpResponseException(statusLine.getStatusCode(), entityString);
        }

        return entityString;

    }

    public static String getNavigationData() {
        String url = "https://api.betfair.com/exchange/betting/rest/v1/en/navigation/menu.json";
        HttpGet get = new HttpGet(url);
        get.setHeader(HTTP_HEADER_X_APPLICATION, prop.getProperty("APPLICATION_KEY"));
        get.setHeader(HTTP_HEADER_X_AUTHENTICATION, prop.getProperty("SESSION_TOKEN"));
        get.setHeader(HTTP_HEADER_CONTENT_TYPE, prop.getProperty("APPLICATION_JSON"));
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Accept-Encoding", "gzip,deflate");

        String response = null;
        try {
            response = HttpClientBuilder.create().build().execute(get, (HttpUtil::handleResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }


    private HttpUtil() {
    }


}
