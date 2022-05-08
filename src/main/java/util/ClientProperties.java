package util;

import api.Operations;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.NoSuchElementException;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
public enum ClientProperties {

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
        try (InputStream in = ClientProperties.class.getResourceAsStream("/API_NG.properties")) {
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
                sslContext.init(getKeyManagers(new FileInputStream(PKCS12_FILE.value()), PKCS12_PASSWORD.value()),
                        trustAllCerts, new SecureRandom());

                String userAndPass = "?username=%s&password=%s".formatted(
                        encode(BETFAIR_USERNAME.value(), UTF_8), encode(BETFAIR_PASSWORD.value(), UTF_8));

                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(BETFAIR_CERT_LOGIN_URL.value() + userAndPass))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .header("X-Application", "apikey")
                        .build();

                String response = HttpClient.newBuilder().sslContext(sslContext).build()
                        .send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

                return getSessionToken(response);
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                     | UnrecoverableKeyException | KeyManagementException | IOException exception) {
                log.fatal("hiba a session token megszerzésekor", exception);
                throw new NoSuchElementException("nincs meg session token", exception);
            } catch (URISyntaxException uriSyntaxException) {
                throw new IllegalStateException(uriSyntaxException);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }


        private static KeyManager[] getKeyManagers(InputStream keyStoreFile, String keyStorePassword)
                throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
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
