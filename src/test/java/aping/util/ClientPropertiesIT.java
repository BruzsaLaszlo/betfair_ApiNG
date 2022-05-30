package aping.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
class ClientPropertiesIT {

    @Autowired
    ClientProperties clientProperties;

    @Test
    void sessionToken() {

        File file = new File("src/main/resources/client-2048.p12");
        assertTrue(file.isFile());
        assertNotNull(clientProperties.getApplicationKey());

        assertNotNull(clientProperties.getSessionToken());
    }
}