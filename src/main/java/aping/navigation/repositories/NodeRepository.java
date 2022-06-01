package aping.navigation.repositories;

import aping.navigation.entities.EventType;
import aping.navigation.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    EventType findNodeByName(String name);

    @Query("SELECT COUNT (et) FROM EventType et")
    long countEventType();

    @Query("SELECT COUNT (e) FROM Event e")
    long countEvent();

    @Query("SELECT COUNT (g) FROM Group g")
    long countGroup();

    @Query("SELECT COUNT (r) FROM Race r")
    long countRace();

    @Query("SELECT COUNT (m) FROM Market m")
    long countMarket();

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
    @Query(value = "DROP TABLE aping.node_event_types;DROP TABLE aping.node_events;DROP TABLE aping.node_groups", nativeQuery = true)
    void dropEventTypes();

    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS=0;", nativeQuery = true)
    void setForeignKeyChecksOff();

    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS=1;", nativeQuery = true)
    void setForeignKeyChecksOn();

    @Modifying
    default void deleteAllFromAllTable() {
        setForeignKeyChecksOff();
        deleteAllFromRace();
        deleteAllFromMarket();
        deleteAllFromEvent();
        deleteAllFromGroup();
        deleteAllFromEventType();
        setForeignKeyChecksOn();
    }

}
