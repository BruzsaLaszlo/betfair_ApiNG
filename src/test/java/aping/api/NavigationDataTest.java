package aping.api;

import aping.navigation.NavigationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;

import static aping.enums.CountryCodes.UNITED_KINGDOM;
import static aping.navigation.NavigationData.*;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("without_db")
class NavigationDataTest {

    @Autowired
    NavigationData navData;

    @BeforeEach
    void createTree() {
        String dataJson = navData.getNavigationDataFromFile();
        navData.createTree(dataJson);
    }

    @Test
    @Disabled("túl nagy file-t tölt le")
    void getNavigationData() {
        navData.updateNavigationData();

        assertThat(navData.getLastUpdateTime()).isCloseTo(now(), byLessThan(1, ChronoUnit.SECONDS));
    }

    @Test
    void sizeOfLists() {

        String s = getSizeOfLists();
        System.out.println(s);
        assertFalse(s.isEmpty());

    }

    @Test
    @DisplayName("NavigationDataTest.test készítés")
    void getAllData() throws IOException {

        var data = navData.getAllData();
        System.out.println(getSizeOfLists());

        Path path = Path.of("src/test/resources/temp/NavigationDataTest.test");
        Files.writeString(path, data);

        assertEquals(data, Files.readString(path));

    }


    @Test
    @DisplayName("Van Soccer az EventType-ok között")
    void isSoccerPresent() {

        boolean isPresent = eventTypes()
                .peek(System.out::println)
                .anyMatch(eventType -> eventType.getName().equals("Soccer"));

        assertTrue(isPresent);

    }

    @Test
    @DisplayName("Van GB és England")
    void hungaryExists() {

        long count = markets()
                .filter(market -> market.getEvent().getCountryCode().equals(UNITED_KINGDOM.code))
                .peek(market -> System.out.println(market.getEvent().getCountryCode()))
                .count();

        assertTrue(count > 100);

        count = markets()
                .filter(market -> market.getEvent().getName().contains("England"))
                .peek(market -> {
                    System.out.println(market.getEvent());
                    System.out.println("    " + market);
                })
                .count();

        assertTrue(count > 0);

    }

    @Test
    @DisplayName("Null-vizsgálat")
    void nullE() {

        long count = markets()
                .filter(market -> market.getEvent() == null)
                .peek(System.out::println)
                .count();

        assertEquals(0, count);

    }


    @Test
    @DisplayName("Magyar meccsek")
    void hungaryMatch() {

        markets()
                .filter(market -> market.getEvent() != null)
                .filter(market -> market.getEvent().getName().contains("Hungary"))
                .findFirst()
                .ifPresent(market -> assertFalse(market.getEvent().getMarkets().isEmpty()));

    }

    @Test
    @DisplayName("Szülö gyerek kapcsolat")
    void sameParentAndEvent() {

        long count = markets()
                .filter(market -> market.getEvent() != market.getParent())
                .peek(System.out::println)
                .count();

        assertEquals(0, count);

    }


}
