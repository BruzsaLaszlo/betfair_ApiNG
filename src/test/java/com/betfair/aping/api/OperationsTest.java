package com.betfair.aping.api;

import com.betfair.aping.ApiNGDemo;
import com.betfair.aping.entities.*;
import com.betfair.aping.enums.ApiNgOperation;
import com.betfair.aping.exceptions.ApiNgException;
import com.betfair.aping.util.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.InaccessibleObjectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

class OperationsTest {


    Operations op = Operations.getInstance();

    @Test
    void makeRequest() throws ApiNgException {


        MarketFilter marketFilter;
        marketFilter = new MarketFilter();
        Set<String> eventTypeIds = new HashSet<>();

        List<EventTypeResult> r = op.listEventTypes(marketFilter);

        for (EventTypeResult eventTypeResult : r) {
            if (eventTypeResult.getEventType().getName().equals("Soccer")) {
                System.out.println("3. EventTypeId for \"Horse Racing\" is: " + eventTypeResult.getEventType().getId() + "\n");
                eventTypeIds.add(eventTypeResult.getEventType().getId());
            }
        }

    }

    @Test
    void accountFunds() throws ApiNgException {
        AccountFundsResponse acr = op.getAccountFunds();
    }



    private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }

    // Create a trust manager that does not validate certificate chains
    final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    @Test
    void con() {
        try {


            SSLContext sslContext = SSLContext.getInstance("SSL");
            URL console = new URL("https://identitysso-cert.betfair.com/api/certlogin");
            if ("https".equals(console.getProtocol())) {
                KeyManager[] km = getKeyManagers("pkcs12", new FileInputStream(new File("src/main/resources/client-2048.p12")), "LACIKa007");
                sslContext.init(km, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection con = (HttpsURLConnection) console.openConnection();
                con.setSSLSocketFactory(sslContext.getSocketFactory());
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setConnectTimeout(30 * 1000);
                con.setReadTimeout(60 * 1000);
                con.setRequestProperty("X-Application", "appkey");

                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("username", "bruzsal"));
                nvps.add(new BasicNameValuePair("password", "lvQ!VkL?DD%6nbkQ*!mw"));

                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(nvps));
                writer.flush();
                writer.close();
                os.close();

                con.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), 1024 * 1024);
                StringBuffer sb = new StringBuffer();
                while (true) {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                System.out.println(sb);
                in.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void conANother() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, KeyManagementException {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(new FileInputStream("test.p12"), "testPass".toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, "testPass".toCharArray());
        KeyManager[] kms = kmf.getKeyManagers();
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream("cacerts"), "changeit".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        TrustManager[] tms = tmf.getTrustManagers();
        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kms, tms, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        URL url = new URL("https://www.testurl.com");
        HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}