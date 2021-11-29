package bruzsal.betfair.api;

import bruzsal.betfair.navigation.Market;
import bruzsal.betfair.navigation.NavigationData;
import bruzsal.betfair.navigation.NavigationDataBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NavigationBejarasTest {

    static NavigationData ND = new NavigationData();

    @BeforeAll
    static void createTree() {
        String dataJson = ND.getNavigationDataFromFile();
        ND.createTree(dataJson);
    }

    @Test
    @DisplayName("NavigationDataTest.test készítés")
    void updateNavigationData() throws IOException {

        var data = ND.getAllData(100);
        ND.getRoot().printToConsole(2);
        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
        Files.writeString(path, data);

        assertEquals(data, Files.readString(path));

    }


    @Test
    @DisplayName("Van Soccer az EventType-ok között")
    void isSoccerPresent() {

        boolean isPresent = NavigationDataBase.EVENT_TYPES.stream()
                .filter(eventType -> eventType.getName().equals("Soccer"))
                .peek(System.out::println)
                .findFirst()
                .isPresent();

        assertTrue(isPresent);

    }

    @Test
    @DisplayName("Van HU és Hungary")
    void hungaryExists() {

        long count = NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent().getCountryCode().equals("HU"))
                .peek(System.out::println)
                .count();

        assertTrue(count > 0);

        count = NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent().getName().contains("Hungary"))
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

        long count = NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent() == null)
                .peek(System.out::println)
                .count();

        assertEquals(0, count);

    }


    @Test
    @DisplayName("Magyar meccsek")
    void hungaryMatch() {

        Optional<Market> m = NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent() != null)
                .filter(market -> market.getEvent().getName().contains("Hungary"))
                .findFirst();

        if (m.isPresent())
            assertFalse(m.get().getEvent().getMarkets().isEmpty());


    }

    @Test
    @DisplayName("Szülö gyerek kapcsolat")
    void sameParentAndEvent() {

        long count = NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent() != market.getParent())
                .peek(System.out::println)
                .count();

        assertEquals(0, count);

    }


}
