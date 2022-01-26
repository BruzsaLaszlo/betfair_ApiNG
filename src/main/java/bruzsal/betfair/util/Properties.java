package bruzsal.betfair.util;

import bruzsal.betfair.api.Operations;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
public enum Properties {

    BETFAIR_USERNAME {
        @Override
        public String value() {
            return PROP.getProperty("BETFAIR_USERNAME");
        }
    },

    BETFAIR_PASSWORD {
        @Override
        public String value() {
            return PROP.getProperty("BETFAIR_PASSWORD");
        }
    },

    BETFAIR_CERT_LOGIN_URL {
        @Override
        public String value() {
            return PROP.getProperty("BETFAIR_CERT_LOGIN_URL");
        }
    },

    PKCS12_FILE {
        @Override
        public String value() {
            return PROP.getProperty("PKCS12_FILE");
        }
    },

    PKCS12_PASSWORD {
        @Override
        public String value() {
            return PROP.getProperty("PKCS12_PASSWORD");
        }
    },

    APPLICATION_KEY {
        @Override
        public String value() {
            return PROP.getProperty("APPLICATION_KEY");
        }
    },

    SESSION_TOKEN {
        @Override
        public String value() {
            return sessionToken;
        }

    };

    public abstract String value();

    private static final java.util.Properties PROP = new java.util.Properties();

    private static String sessionToken;

    static {
        try (InputStream in = Properties.class.getResourceAsStream("/apingdemo.properties")) {

            PROP.load(in);
            sessionToken = updateSessionToken();

        } catch (IOException e) {
            log.fatal("Error loading the properties file: ", e);
            System.exit(-1);
        }
    }

    public static String updateSessionToken() {
        sessionToken = SessionTokenService.get();
        return sessionToken;
    }

    public static String sessionToken() {
        return sessionToken;
    }

    private static class SessionTokenService {

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

                List<NameValuePair> nvps = List.of(
                        new BasicNameValuePair("username", BETFAIR_USERNAME.value()),
                        new BasicNameValuePair("password", BETFAIR_PASSWORD.value()));

                try (OutputStream os = con.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(
                             new OutputStreamWriter(os, StandardCharsets.UTF_8))) {
                    writer.write(getQuery(nvps));
                }

                con.connect();

                StringBuilder sb = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), 1024 * 1024)) {
                    while (in.ready()) {
                        String line = in.readLine();
                        if (line == null) break;
                        sb.append(line);
                    }
                }
                return getSessionToken(sb.toString());
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                    | UnrecoverableKeyException | KeyManagementException | IOException exception) {
                log.fatal("hiba a session token megszerzésekor", exception);
                throw new NoSuchElementException("nincs meg session token", exception);
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
            return params.stream()
                    .map(nameValuePair -> URLEncoder.encode(nameValuePair.getName(), StandardCharsets.UTF_8)
                            + "="
                            + URLEncoder.encode(nameValuePair.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));
        }

        private record Session(
                String sessionToken,
                String loginStatus
        ) {
        }

        public static String getSessionToken(String dataJson) {
            try {
                Session session = Operations.om.readValue(dataJson, Session.class);
                if (session.loginStatus.equals("SUCCESS")) {
                    log.info("LoginsStatus: " + session.loginStatus);
                    log.info("SessionToken: " + session.sessionToken);
                    return session.sessionToken;
                }
                log.fatal("NO SESSION TOKEN!");
                throw new IllegalStateException("nem sikerült megszerezni a sessiont token-t");
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("Sikertelen a session token megszerzése");
            }
        }

        private SessionTokenService() {
        }
    }

}
