package bruzsal.betfair.api;

import bruzsal.betfair.navigation.NavigationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static bruzsal.betfair.enums.CountryCodes.UNITED_KINGDOM;
import static bruzsal.betfair.navigation.NavigationData.*;
import static org.junit.jupiter.api.Assertions.*;

class NavigationDataTest {

    static NavigationData ND = new NavigationData();

    @BeforeAll
    static void createTree() throws JsonProcessingException {
        String dataJson = ND.getNavigationDataFromFile();
        ND.createTree(dataJson);
    }

    @Test
    void sizeOfLists() {

        String s = getSizeOfLists();
        System.out.println(s);
        assertFalse(s.isEmpty());

    }

    @Test
    @DisplayName("NavigationDataTest.test készítés")
    void updateNavigationData() throws IOException {

        var data = ND.getAllData();

        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
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
                .filter(market -> market.getEvent().getCountryCode().equals(UNITED_KINGDOM.CODE))
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
