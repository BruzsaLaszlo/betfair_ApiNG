package com.betfair.aping.util;


import com.betfair.aping.ApiNGDemo;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public final class HttpUtil {

    private static final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private static final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";


    public static String sendPostRequest(String jsonRequest, String operation) {

        String url = ApiNGDemo.getProp().getProperty("APING_URL") + ApiNGDemo.getProp().getProperty("RESCRIPT_SUFFIX") + operation + "/";

        HttpPost post = new HttpPost(url);
        post.setHeader(HTTP_HEADER_CONTENT_TYPE, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, "UTF-8");
        post.setHeader(HTTP_HEADER_X_APPLICATION, ApiNGDemo.getProp().getProperty("APPLICATION_KEY"));
        post.setHeader(HTTP_HEADER_X_AUTHENTICATION, ApiNGDemo.getProp().getProperty("SESSION_TOKEN"));
        post.setEntity(new StringEntity(jsonRequest, "UTF-8"));

        try {

            return HttpClientBuilder.create().build().execute(post, new RescriptResponseHandler());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private HttpUtil() {
    }


}
