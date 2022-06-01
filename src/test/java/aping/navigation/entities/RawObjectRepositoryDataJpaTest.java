package aping.navigation.entities;

import aping.enums.CountryCodes;
import aping.navigation.repositories.RawObjectRepository;
import aping.util.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "classpath:db/migration/V3__raw_object.sql")
class RawObjectRepositoryDataJpaTest {

    @Autowired
    RawObjectRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        String jsonData = Files.readString(Path.of("c:\\temp\\NavigationData.json"));
        RawObject rootRawObject = new JsonMapper().readValue(jsonData, RawObject.class);
        repository.save(rootRawObject);
    }

    @Test
    void findRawObjectByCountryCode() {
        System.out.println("RawObject count: " + repository.count());
        assertThat(repository.findRawObjectByCountryCode(CountryCodes.UNITED_KINGDOM.code)).isNotEmpty();
    }
}