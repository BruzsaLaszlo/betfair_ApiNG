package com.betfair.aping.api;

import com.betfair.aping.navigation.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationDataFileTest {


    class Child {

        public String type;
        public String name;
        public String id;
        public String exchangeId;
        public String marketType;
        public Date marketStartTime;
        public String numberOfWinners;
        public String countryCode;
        public List<Child> children;
        public String venue;
        public Date startTime;
        public String raceNumber;

    }


    @Test
    void makeObjects() throws IOException {

        String data = Files.readString(OperationsTest.NAVIGATION_DATA_JSON);

        Child rootJson = Operations.gson.fromJson(data, Child.class);

        Root root = new Root("1", "ROOT");

        bejaras(rootJson, root, 0);

//        root.printToConsole(2);

        root.statics();
//        System.out.printf("Statt" + root.statics(););

        System.out.printf("");

        StringBuilder sb = new StringBuilder(5_000_000);

        root.getAllFormatToFile(sb,2);

        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
        Files.writeString(path, sb.toString());

        assertEquals(sb.toString(), Files.readString(path));
//        bejaras(o, 0);

    }

    private void add(NavigationData o, Group g) {
        if (o instanceof EventType)
            ((EventType) o).getGroups().add(g);
        else if (o instanceof Group)
            ((Group) o).getGroups().add(g);
        else if (o instanceof Event)
            ((Event) o).getGroups().add(g);
    }

    private void add(NavigationData o, Event e) {
        if (o instanceof EventType)
            ((EventType) o).getEvents().add(e);
        else if (o instanceof Group)
            ((Group) o).getEvents().add(e);
        else if (o instanceof Event)
            ((Event) o).getEvents().add(e);
    }

    private void add(NavigationData o, Market m) {
        if (o instanceof Event)
            ((Event) o).getMarkets().add(m);
        else ((Race) o).getMarkets().add(m);
    }

    private void bejaras(Child root, NavigationData o, int deep) {

        NavigationData nd = null;
        switch (root.type) {
            case "EVENT_TYPE":
                nd = new EventType(root.id, root.name);
                ((Root) o).events.add((EventType) nd);
                break;
            case "GROUP":
                if (root.name.equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(root.id, root.name);
                add(o, (Group) nd);
                break;
            case "EVENT":
                nd = new Event(root.id, root.name, root.countryCode);
                add(o, (Event) nd);
                break;
            case "RACE":
                nd = new Race(root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
                ((EventType) o).getRaces().add((Race) nd);
                break;
            case "MARKET":
                nd = new Market(root.exchangeId, root.id, root.marketStartTime, root.marketType, root.numberOfWinners, root.name);
                add(o, (Market) nd);
                break;
        }

        if ((o = nd) == null) return;
        nd.setMelyseg(deep);


        if (root.children != null) {
            for (Child c : root.children) {
                bejaras(c, o, deep + 1);
            }
        }
    }

    private void bejaras(NavigationData root, int i) {
//        if (root.isChildren()) {
//            for (Child c : root.children) {
//                count++;
//                sb.append(spaces[i]).append(c.name).append("\n");
//                bejaras(c, i + 1);
//            }
//        }
    }

    @Nested
    @Description("A navigation data-t kiirja fileba es a consolra" + "c:\\temp\\NavigationDataTest.test")
    class navigationDataToFile {

        @Test
        void test() throws IOException {

            String data = Files.readString(OperationsTest.NAVIGATION_DATA_JSON);

            Child root = Operations.gson.fromJson(data, Child.class);

            assertNotNull(root);
            assertFalse(root.children.isEmpty());


            bejaras(root, 0);

            System.out.println(sb);

            Files.writeString(Path.of("c:\\temp\\NavigationDataTest.test"), sb.toString());

        }

        static final String[] spaces = {
                "",
                "    ",
                "         ",
                "              ",
                "                   ",
                "                        ",
                "                             ",
        };

        StringBuilder sb = new StringBuilder();
        int count = 0;

        private void bejaras(Child root, int i) {
            if (root.children != null) {
                for (Child c : root.children) {
                    count++;
                    sb.append(spaces[i]).append(c.name).append("\n");
                    bejaras(c, i + 1);
                }
            }
        }

    }

}
