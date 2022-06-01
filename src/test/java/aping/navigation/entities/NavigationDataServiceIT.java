package aping.navigation.entities;

import aping.navigation.dtos.RawObjectDto;
import aping.navigation.services.NavigationDataService;
import aping.util.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class NavigationDataServiceIT {

    @Autowired
    NavigationDataService service;

    @Autowired
    JsonMapper jsonMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Disabled
    void saveTree() {
        service.saveTree(service.getNavigationDataFromFile());
        service.getAllDataFromFile();
    }

    @Test
    void saveRoot() {
        service.saveWithOnlyRoot();
    }

    @Test
    void saveRootWithCascadeAll() {
        service.saveWithOnlyRoot();
    }

    @Test
    void updateNavigationData() {
        service.updateNavigationData();
    }

    @Test
    void testReadDataFromFile() {
        assertThatNoException().isThrownBy(() ->
                new JsonMapper().readValue(service.getNavigationDataFromFile(), RawObjectDto.class));
    }

    @Disabled("Save to database")
    @Test
    void persistFromJSON() {
        assertThatNoException().isThrownBy(() -> service.saveAll());
    }

    @Test
    void countEntities() {
        System.out.println(service.getEntitiesCount());
        ;
    }
}
