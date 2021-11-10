package com.betfair.aping.api;

import com.betfair.aping.navigation.NavigationData;
import com.betfair.aping.navigation.Root;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationBejarasTest {

    static {
        Root root = Root.getInstance();
        String dataJson = null;
        try {
            dataJson = root.getNavigationDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.createTree(dataJson);
    }


    @Test
    void navdataFunctionTest() {

        NavigationData.allEvenType.forEach(System.out::println);
        NavigationData.allEvenType.stream()
                .filter(eventType -> eventType.getName().equals("Soccer"))
                .findFirst().ifPresent(System.out::println);

    }

    @Test
    void overUnder25() {

        NavigationData.allMarket.stream()
                .filter(market -> market.getMarketType().equals("OVER_UNDER_25"))
                .filter(market -> market.getEvent().getCountryCode().equals("BR"))
                .forEach(market -> {
                    System.out.println(market.getEvent());
                    System.out.println("    " + market);
                });

    }

    @Test
    @Description("segéd metódus")
    void updateNavigationData() throws IOException {

        var data = Root.getInstance().getAllData(10);
        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
        Files.writeString(path, data);
    }

}
