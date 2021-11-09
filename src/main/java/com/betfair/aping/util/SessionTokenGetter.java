package com.betfair.aping.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class SessionTokenGetter {

    public static String getSessionToken()
            throws NoSuchAlgorithmException, KeyManagementException, IOException, UnrecoverableKeyException, CertificateException, KeyStoreException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        URL console = new URL("https://identitysso-cert.betfair.com/api/certlogin");
        KeyManager[] km = getKeyManagers("pkcs12", new FileInputStream("src/main/resources/client-2048.p12"), "LACIKa007");
        sslContext.init(km, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection con = (HttpsURLConnection) console.openConnection();
        con.setSSLSocketFactory(sslContext.getSocketFactory());
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setConnectTimeout(30 * 1000);
        con.setReadTimeout(60 * 1000);
        con.setRequestProperty("X-Application", "apikey");

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username", "bruzsal"));
        nvps.add(new BasicNameValuePair("password", "lvQ!VkL?DD%6nbkQ*!mw"));

        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(getQuery(nvps));
        writer.flush();
        writer.close();
        os.close();

        con.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), 1024 * 1024);
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        System.out.println(sb);
        in.close();
        return sb.toString().split("\"")[3];
    }

    private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }

    // Create a trust manager that does not validate certificate chains
    static final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    // TODO document why this method is empty
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    // TODO document why this method is empty
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    private static String getQuery(List<NameValuePair> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), StandardCharsets.UTF_8));
        }

        return result.toString();
    }

    private SessionTokenGetter() {
    }
}
