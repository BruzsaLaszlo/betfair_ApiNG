package aping.navigation.entities;

import aping.util.JsonMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class NavigationDataServiceIT {

    @Autowired
    NavigationDataService service;

    @PersistenceContext
    EntityManager em;

    @Test
    @Disabled
    void saveTree() {
//        EntityManager ma = factory.createEntityManager();
//        ma.getTransaction().begin();
//
//        ma.persist(new Market(1,"1", "marketke",null,"sd2121fsd",null));
//        ma.persist(new Group(1,"2121sdf", "grouppp"));
//
//        ma.getTransaction().commit();
//        ma.close();
        service.saveTree(service.getNavigationDataFromFile());
        service.getAllData();
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
    @DisplayName("Van Soccer az EventType-ok között")
    void isSoccerPresent() {

//        boolean isPresent = eventTypes()
//                .peek(System.out::println)
//                .anyMatch(eventType -> eventType.getName().equals("Soccer"));
//
//        assertTrue(isPresent);
        EventType singleResult = em
                .createQuery("select et from EventType et where et.name = 'Soccer'", EventType.class)
                .getSingleResult();
        assertThat(singleResult.name).isEqualTo("Soccer");

    }

    @Test
    void getMarketTypes() {
        String marketTypes = em
                .createQuery("select distinct r.marketType from RawObject r", String.class)
                .getResultStream().collect(Collectors.joining(",\n"));
        assertThat(marketTypes).isNotBlank();
        System.out.println(marketTypes);
    }

    @Test
    void getTypes() {
        String types = em
                .createQuery("select distinct r.type from RawObject r", String.class)
                .getResultStream().collect(Collectors.joining(",\n"));
        assertThat(types).isNotBlank();
        System.out.println(types);
    }

    @Test
    void getHungarians() {
        String hun = em
                .createQuery("select r.name from RawObject r where r.countryCode = :hu", String.class)
                .setParameter("hu", "HU")
                .getResultStream()
                .collect(Collectors.joining("\n"));

        assertThat(hun).isNotBlank();
        System.out.println(hun);
    }
}
