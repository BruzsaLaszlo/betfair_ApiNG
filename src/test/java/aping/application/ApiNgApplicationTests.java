package aping.application;

import aping.util.ClientProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(value = ClientProperties.class)
class ApiNgApplicationTests {

    @Test
    void contextLoads() {

    }

}
