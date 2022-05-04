package navigation.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;

public abstract class Repository {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("aping_pu");
    //        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test_pu");


    @AfterEach
    void tearDown() {
        factory.close();
    }

    @FunctionalInterface
    interface Transactional {
        void execute(EntityManager manager);
    }

    @FunctionalInterface
    interface Transactionaless<R> {
        R execute(EntityManager manager);
    }

    void transactional(Transactional transactional) {
        try (EntityManager manager = factory.createEntityManager()) {
            manager.getTransaction().begin();
            transactional.execute(manager);
            manager.getTransaction().commit();
        }
    }

    <R> R transactionaless(Transactionaless<R> transactionaless) {
        EntityManager manager = factory.createEntityManager();
        R result = transactionaless.execute(manager);
        manager.close();
        return result;
    }
}
