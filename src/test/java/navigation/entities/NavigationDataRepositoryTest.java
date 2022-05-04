package navigation.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.JsonMapper;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatNoException;

class NavigationDataRepositoryTest {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("aping_pu");
    //    EntityManagerFactory factory = Persistence.createEntityManagerFactory("test_pu");
    NavigationDataRepository repository = new NavigationDataRepository(factory);

    @AfterEach
    void tearDown() {
        factory.close();
    }

    @Test
    @Disabled
    void name() throws JsonProcessingException {
//        EntityManager ma = factory.createEntityManager();
//        ma.getTransaction().begin();
//
//        ma.persist(new Market(1,"1", "marketke",null,"sd2121fsd",null));
//        ma.persist(new Group(1,"2121sdf", "grouppp"));
//
//        ma.getTransaction().commit();
//        ma.close();
        repository.createTree(repository.getNavigationDataFromFile());
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
