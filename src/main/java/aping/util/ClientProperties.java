package aping.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aping")
public class ClientProperties {

    @Autowired
    HttpClient httpClient;

    private String betfairUsername;
    private String betfairPassword;
    private String betfairCertLoginUrl;
    private String pkcs12File;
    private String pkcs12Password;

    @Getter
    private String applicationKey;

    private String sessionToken;

    public String getSessionToken() {
        return sessionToken == null ? updateSessionToken() : sessionToken;
    }

    public String updateSessionToken() {
        sessionToken = new SessionTokenService().get();
        return sessionToken;
    }

    private class SessionTokenService {

        public String get() {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(getKeyManagers(new FileInputStream(pkcs12File)),
                        trustAllCerts, new SecureRandom());

                String userAndPass = "?username=%s&password=%s".formatted(
                        encode(betfairUsername, UTF_8), encode(betfairPassword, UTF_8));

                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(betfairCertLoginUrl + userAndPass))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .header("X-Application", "apikey")
                        .build();

                String response = HttpClient.newBuilder().sslContext(sslContext).build()
                        .send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

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


        private KeyManager[] getKeyManagers(InputStream keyStoreFile)
                throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            keyStore.load(keyStoreFile, pkcs12Password.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, pkcs12Password.toCharArray());
            return kmf.getKeyManagers();
        }

        // Create a trust manager that does not validate certificate chains
        private TrustManager[] trustAllCerts = new TrustManager[]{
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
