package com.betfair.aping.util;


import com.betfair.aping.ApiNGDemo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public final class HttpUtil {

    private static final String HTTP_HEADER_X_APPLICATION = "X-Application";
    private static final String HTTP_HEADER_X_AUTHENTICATION = "X-Authentication";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";
    private static final String HTTP_HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String CHARSET_UTF8 = "UTF-8";


    public static String sendPostRequest(String operation, String jsonRequest) throws IOException {

//        String url = ApiNGDemo.getProp().getProperty("SPORT_APING_URL") + ApiNGDemo.getProp().getProperty("RESCRIPT_SUFFIX") + operation + "/";
        String url = ApiNGDemo.getProp().getProperty("ACCOUNT_APING_URL") + ApiNGDemo.getProp().getProperty("RESCRIPT_SUFFIX") + operation + "/";

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
        ApiNGDemo.debug("URL: ",post.getURI().toString());
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


    public static String sendPostRequestAccount(String operation, String param) {
        class JsonResponseHandler implements ResponseHandler<String> {
            private static final String ENCODING_UTF_8 = "UTF-8";

            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();
                return entity == null ? null : EntityUtils.toString(entity,ENCODING_UTF_8);

            }
        }

        String url = ApiNGDemo.getProp().getProperty("ACCOUNT_APING_URL") + ApiNGDemo.getProp().getProperty("JSON_RPC_SUFFIX");
        String jsonRequest = param;

        HttpPost post = new HttpPost(url);
        String resp = null;
        try {
            post.setHeader(HTTP_HEADER_CONTENT_TYPE, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
            post.setHeader(HTTP_HEADER_ACCEPT, ApiNGDemo.getProp().getProperty("APPLICATION_JSON"));
            post.setHeader(HTTP_HEADER_ACCEPT_CHARSET, ApiNGDemo.getProp().getProperty("ENCODING_UTF8"));
            post.setHeader(HTTP_HEADER_X_APPLICATION, ApiNGDemo.getProp().getProperty("APPLICATION_KEY"));
            post.setHeader(HTTP_HEADER_X_AUTHENTICATION, ApiNGDemo.getProp().getProperty("SESSION_TOKEN"));

            post.setEntity(new StringEntity(jsonRequest, ApiNGDemo.getProp().getProperty("ENCODING_UTF8")));

            resp = HttpClientBuilder.create().build().execute(post, new JsonResponseHandler());

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException ioE) {
            System.out.println("class not found");
        }

        return resp;

    }


    private HttpUtil() {
    }


}
