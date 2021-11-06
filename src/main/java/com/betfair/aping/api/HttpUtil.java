package com.betfair.aping.api;


import com.betfair.aping.ApiNGDemo;
import com.betfair.aping.util.RescriptResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class HttpUtil {

    private static final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private static final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";

    public HttpUtil() {
        super();
    }

    private String sendPostRequest(String param, String operation, String appKey, String ssoToken, String URL, ResponseHandler<String> reqHandler) {
        String jsonRequest = param;

        HttpPost post = new HttpPost(URL);
        String resp = null;
        post.setHeader(HTTP_HEADER_CONTENT_TYPE, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
        post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, ApiNGDemo.getProp().getProperty("ENCODING_UTF8"));
        post.setHeader(HTTP_HEADER_X_APPLICATION, appKey);
        post.setHeader(HTTP_HEADER_X_AUTHENTICATION, ssoToken);

        post.setEntity(new StringEntity(jsonRequest, ApiNGDemo.getProp().getProperty("ENCODING_UTF8")));

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            resp = httpClient.execute(post, reqHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;

    }

    public String sendPostRequestRescript(String param, String operation, String appKey, String ssoToken) {
        String apiNgURL = ApiNGDemo.getProp().getProperty("APING_URL") + ApiNGDemo.getProp().getProperty("RESCRIPT_SUFFIX") + operation + "/";

        return sendPostRequest(param, operation, appKey, ssoToken, apiNgURL, new RescriptResponseHandler());

    }

}
