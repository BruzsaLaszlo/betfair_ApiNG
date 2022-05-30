package aping.util;

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
import java.util.Properties;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
public abstract class ClientProperties {

    private ClientProperties() {
    }

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream in = ClientProperties.class.getResourceAsStream("/API_NG.properties")) {
            PROPERTIES.load(in);
        } catch (IOException e) {
            log.fatal("Error loading the properties file: ", e);
            System.exit(-1);
        }
    }

    private static final String BETFAIR_USERNAME = PROPERTIES.getProperty("BETFAIR_USERNAME");
    private static final String BETFAIR_PASSWORD = PROPERTIES.getProperty("BETFAIR_PASSWORD");
    private static final String BETFAIR_CERT_LOGIN_URL = PROPERTIES.getProperty("BETFAIR_CERT_LOGIN_URL");
    private static final String PKCS12_FILE = PROPERTIES.getProperty("PKCS12_FILE");
    private static final String PKCS12_PASSWORD = PROPERTIES.getProperty("PKCS12_PASSWORD");
    public static final String APPLICATION_KEY = PROPERTIES.getProperty("APPLICATION_KEY");

    private static String sessionToken = updateSessionToken();

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
                sslContext.init(getKeyManagers(new FileInputStream(PKCS12_FILE)),
                        trustAllCerts, new SecureRandom());

                String userAndPass = "?username=%s&password=%s".formatted(
                        encode(BETFAIR_USERNAME, UTF_8), encode(BETFAIR_PASSWORD, UTF_8));

                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(BETFAIR_CERT_LOGIN_URL + userAndPass))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .header("X-Application", "apikey")
                        .build();

                System.out.println(httpRequest.uri());

                String response = HttpClient.newBuilder().sslContext(sslContext).build()
                        .send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

                System.out.println(response);

                return new JsonMapper().readValue(response, Session.class).sessionToken;
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


        private static KeyManager[] getKeyManagers(InputStream keyStoreFile)
                throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            keyStore.load(keyStoreFile, ClientProperties.PKCS12_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, ClientProperties.PKCS12_PASSWORD.toCharArray());
            return kmf.getKeyManagers();
        }

        // Create a trust manager that does not validate certificate chains
        private static final TrustManager[] trustAllCerts = new TrustManager[]{
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

        private SessionTokenService() {
        }
    }

}
