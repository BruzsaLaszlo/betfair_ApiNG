package com.betfair.aping.api;

import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.enums.MarketProjection;
import com.betfair.aping.enums.MarketSort;
import com.betfair.aping.exceptions.ApiNgException;
import com.betfair.aping.navigation.NavigationData;
import com.betfair.aping.navigation.Root;
import jdk.jfr.Description;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Description("segéd metódus")
    void updateNavigationData() throws IOException {

        var data = Root.getInstance().getAllData(10);
        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
        Files.writeString(path, data);
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
//                .filter(market -> market.getMarketType().equals("OVER_UNDER_25"))
                .filter(market -> market.getEvent().getCountryCode().equals("") && market.getEvent().getName().contains("Hungary"))
                .forEach(market -> {
                    System.out.println(market.getEvent());
                    System.out.println("    " + market);
                });

    }

    @Test
    void nullE() throws ApiNgException {
        NavigationData.allMarket.stream()
                .filter(market -> market.getEvent() == null)
                .forEach(System.out::println);
        long count = NavigationData.allMarket.stream()
                .filter(market -> market.getEvent() == null)
                .count();
        assertEquals(0, count);
    }


    @Test
    void atoolfugg() throws ApiNgException {

        Operations operations = new Operations();

        MarketFilter mf = new MarketFilter();
        mf.setMarketIds(Set.of("1.190656015"));

        List<EventTypeResult> erl = operations.listEventTypes(mf);

        var smp = new HashSet<MarketProjection>();
        smp.add(MarketProjection.MARKET_DESCRIPTION);
        smp.add(MarketProjection.COMPETITION);

        List<MarketCatalogue> mcl = operations.listMarketCatalogue(mf, smp, MarketSort.MAXIMUM_AVAILABLE, 10);
        mcl.forEach(System.out::println);

//        erl.forEach(er -> er.setEventType());


    }

    @Test
    void hungaryMatch() {

        NavigationData.allMarket.stream()
                .filter(market -> market.getEvent() != null)
                .filter(market -> market.getEvent().getName().contains("Hungary"))
                .filter(market -> market.getEvent().getName().contains("San"))
                .findFirst()
                .get()
                .getEvent().getMarkets().forEach(System.out::println);


    }



}
