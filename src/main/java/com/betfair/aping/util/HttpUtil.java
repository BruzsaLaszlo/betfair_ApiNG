package com.betfair.aping.util;


import com.betfair.aping.ApiNGDemo;
import com.betfair.aping.enums.Endpoint;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

public final class HttpUtil {

    private static final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private static final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";
    private static final String HTTP_HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String CHARSET_UTF8 = "UTF-8";


    public static String sendPostRequest(String operation, String jsonRequest, Endpoint endpoint) throws IOException {

        String url;
        if (endpoint == Endpoint.ACCOUNT)
            url = ApiNGDemo.getProp().getProperty("ACCOUNT_APING_URL") + ApiNGDemo.getProp().getProperty("RESCRIPT_SUFFIX") + operation + "/";
        else
            url = ApiNGDemo.getProp().getProperty("SPORT_APING_URL") + ApiNGDemo.getProp().getProperty("RESCRIPT_SUFFIX") + operation + "/";

        HttpPost post = new HttpPost(url);
        post.setHeader(HTTP_HEADER_CONTENT_TYPE, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, CHARSET_UTF8);
        post.setHeader(HTTP_HEADER_X_APPLICATION, ApiNGDemo.getProp().getProperty("APPLICATION_KEY"));
        post.setHeader(HTTP_HEADER_X_AUTHENTICATION, ApiNGDemo.getProp().getProperty("SESSION_TOKEN"));
        post.setHeader(HTTP_HEADER_ACCEPT_ENCODING, ApiNGDemo.getProp().getProperty(HTTP_HEADER_ACCEPT_ENCODING));
        post.setHeader("Connection", "keep-alive");
        if (jsonRequest != null)
            post.setEntity(new StringEntity(jsonRequest, CHARSET_UTF8));

        ApiNGDemo.debug("Request headers", Arrays.toString(post.getAllHeaders()));
        ApiNGDemo.debug("URL: ", post.getURI().toString());
        ApiNGDemo.debug("Request json", jsonRequest);

        return HttpClientBuilder.create().build().execute(post, (httpResponse -> {

            HttpEntity entity = httpResponse.getEntity();
            String entityString = entity == null ? "null" : EntityUtils.toString(entity, CHARSET_UTF8);
            ApiNGDemo.debug("Response", entityString);

            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() != 200) {
                throw new HttpResponseException(statusLine.getStatusCode(), entityString);
            }

            return entityString;

        }));

    }


    private HttpUtil() {
    }


}
