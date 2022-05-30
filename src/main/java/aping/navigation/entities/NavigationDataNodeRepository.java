package aping.navigation.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationDataNodeRepository extends JpaRepository<Node, Long> {

    @Modifying
    @Query("DELETE FROM EventType e")
    void deleteAllFromEventType();

    @Modifying
    @Query("DELETE FROM Event e")
    void deleteAllFromEvent();

    @Modifying
    @Query("DELETE FROM Group e")
    void deleteAllFromGroup();

    @Modifying
    @Query("DELETE FROM Market e")
    void deleteAllFromMarket();

    @Modifying
    @Query("DELETE FROM Race e")
    void deleteAllFromRace();

    @Modifying
    default void deleteAllFromAllTable() {
        deleteAllFromEventType();
        deleteAllFromEvent();
        deleteAllFromGroup();
        deleteAllFromMarket();
        deleteAllFromRace();
    }

}
