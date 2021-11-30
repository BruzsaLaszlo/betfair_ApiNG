package bruzsal.betfair.api;

import bruzsal.betfair.enums.CountryCodes;
import bruzsal.betfair.navigation.NavigationData;
import bruzsal.betfair.navigation.NavigationDataBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static bruzsal.betfair.enums.CountryCodes.*;

class NavigationDataTest {

    static NavigationData ND = new NavigationData();

    @Test
    void getCountryCodes() throws IOException {
        List<String> listOfCodes = Files.readAllLines(Path.of("./src/main/resources/iso_code.txt"));
        for (String s : listOfCodes) {
            if (s.startsWith("\"")) {
                var ss = s.split("\"");
                String name = ss[1].replace(",", "").replaceAll("\\s+", "_");
                var sa = ss[2].split(",");
                String a2 = sa[1];
                String a3 = sa[2];
                System.out.printf("%s(\"%s\", \"%s\"),%n", name, a2, a3);
            } else {
                var sa = s.split(",");
                sa[0] = sa[0].replaceAll("[ ,-]", "_").replaceAll("([(].+[)]|[-])", "");
//            sa[0] = sa[0].replaceAll("([(].+[)]|[-])", "");
//            sa[0] = sa[0].replaceAll("\\s+", "_");
                System.out.printf("%s(\"%s\", \"%s\"),%n", sa[0], sa[1], sa[2]);
            }
        }
    }

    @BeforeAll
    static void createTree() throws JsonProcessingException {
        String dataJson = ND.getNavigationDataFromFile();
        ND.createTree(dataJson);
    }

    @Test
    void databaseTest() {

        String s = NavigationDataBase.getSizeOfLists();
        System.out.println(s);
        assertFalse(s.isEmpty());

    }

    @Test
    @DisplayName("NavigationDataTest.test készítés")
    void updateNavigationData() throws IOException {

        var data = ND.getAllData(100);

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
                .filter(market -> market.getEvent().getCountryCode().equals(HUNGARY.CODE))
                .peek(System.out::println)
                .count();

        assertTrue(count > 0);

        count = NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent().getName().contains(HUNGARY.name()))
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

        NavigationDataBase.MARKETS.stream()
                .filter(market -> market.getEvent() != null)
                .filter(market -> market.getEvent().getName().contains("Hungary"))
                .findFirst()
                .ifPresent(market -> assertFalse(market.getEvent().getMarkets().isEmpty()));

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
