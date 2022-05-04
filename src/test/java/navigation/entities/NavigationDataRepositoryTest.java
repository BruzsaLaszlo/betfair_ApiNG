package navigation.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.JsonMapper;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class NavigationDataRepositoryTest extends Repository {


    NavigationDataRepository repository = new NavigationDataRepository(factory);

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    @Disabled
    void saveTree() throws JsonProcessingException {
//        EntityManager ma = factory.createEntityManager();
//        ma.getTransaction().begin();
//
//        ma.persist(new Market(1,"1", "marketke",null,"sd2121fsd",null));
//        ma.persist(new Group(1,"2121sdf", "grouppp"));
//
//        ma.getTransaction().commit();
//        ma.close();
        repository.saveTree(repository.getNavigationDataFromFile());
    }

    @Test
    void testReadDataFromFile() throws JsonProcessingException {
        assertThatNoException().isThrownBy(() ->
                new JsonMapper().readValue(repository.getNavigationDataFromFile(), RawObjectDto.class));
    }

    @Disabled("Save to database")
    @Test
    void persistFromJSON() throws JsonProcessingException {
        assertThatNoException().isThrownBy(() -> repository.saveAll());
    }

    @Test
    @DisplayName("Van Soccer az EventType-ok között")
    void isSoccerPresent() {

//        boolean isPresent = eventTypes()
//                .peek(System.out::println)
//                .anyMatch(eventType -> eventType.getName().equals("Soccer"));
//
//        assertTrue(isPresent);
        EventType singleResult = transactionaless(manager -> manager
                .createQuery("select et from EventType et where et.name = 'Soccer'", EventType.class))
                .getSingleResult();
        assertThat(singleResult.name).isEqualTo("Soccer");

    }

    @Test
    void getMarketTypes() {
        String marketTypes = factory.createEntityManager()
                .createQuery("select distinct r.marketType from RawObject r", String.class)
                .getResultStream().collect(Collectors.joining(",\n"));
        System.out.println(marketTypes);
    }

    @Test
    void getTypes() {
        String types = factory.createEntityManager()
                .createQuery("select distinct r.type from RawObject r", String.class)
                .getResultStream().collect(Collectors.joining(",\n"));
        System.out.println(types);
    }

    @Test
    void getHungarians() {
        String hun = factory.createEntityManager()
                .createQuery("select r.name from RawObject r where r.countryCode = :hu", String.class)
                .setParameter("hu", "HU")
                .getResultStream()
                .collect(Collectors.joining("\n"));

        System.out.println(hun);
    }
}
