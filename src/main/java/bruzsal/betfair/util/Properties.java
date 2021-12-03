package bruzsal.betfair.util;

import bruzsal.betfair.api.Operations;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public enum Properties {

    BETFAIR_USERNAME {
        @Override
        public String value() {
            return prop.getProperty("BETFAIR_USERNAME");
        }
    },

    BETFAIR_PASSWORD {
        @Override
        public String value() {
            return prop.getProperty("BETFAIR_PASSWORD");
        }
    },

    BETFAIR_CERT_LOGIN_URL {
        @Override
        public String value() {
            return prop.getProperty("BETFAIR_CERT_LOGIN_URL");
        }
    },

    PKCS12_FILE {
        @Override
        public String value() {
            return prop.getProperty("PKCS12_FILE");
        }
    },

    PKCS12_PASSWORD {
        @Override
        public String value() {
            return prop.getProperty("PKCS12_PASSWORD");
        }
    },

    APPLICATION_KEY {
        @Override
        public String value() {
            return prop.getProperty("APPLICATION_KEY");
        }
    },

    SESSION_TOKEN {
        @Override
        public String value() {
            return sessionToken;
        }

    };


    public abstract String value();

    public static boolean debug;

    private static final java.util.Properties prop = new java.util.Properties();

    public static String sessionToken;

    static {
        try (InputStream in = Properties.class.getResourceAsStream("/apingdemo.properties")) {

            prop.load(in);
            debug = Boolean.parseBoolean(prop.getProperty("DEBUG"));
            sessionToken = updateSessionToken();

        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e);
        }
    }

    public static String updateSessionToken() {
        return sessionToken = SessionTokenGetter.get();
    }

    private static class SessionTokenGetter {

        public static String get() {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                URL console = new URL(BETFAIR_CERT_LOGIN_URL.value());
                KeyManager[] km = getKeyManagers("pkcs12", new FileInputStream(PKCS12_FILE.value()), PKCS12_PASSWORD.value());
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
                nvps.add(new BasicNameValuePair("username", BETFAIR_USERNAME.value()));
                nvps.add(new BasicNameValuePair("password", BETFAIR_PASSWORD.value()));

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
                while (in.ready()) {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                in.close();

                String sessionToken = getSessionToken(sb.toString());

                return sessionToken;
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                    | UnrecoverableKeyException | KeyManagementException | IOException exception) {
                exception.printStackTrace();
                throw new IllegalStateException("nincs session token", exception);
            }
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

        private record SessionToken(
                String sessionToken,
                String loginStatus
        ) {
        }

        public static String getSessionToken(String dataJson) {
            try {
                SessionToken st = Operations.om.readValue(dataJson, SessionToken.class);
                if (st.loginStatus.equals("SUCCESS")) {
                    if (Properties.debug)
                        System.out.printf("LoginsStatus: %s%nSessionToken: %s%n", st.loginStatus, st.sessionToken);
                    return st.sessionToken;
                }
                throw new IllegalStateException("nem sikerült megszerezni a sessiont token-t");
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("Sikertelen a session token megszerzése");
            }
        }

        private SessionTokenGetter() {
        }
    }

}
