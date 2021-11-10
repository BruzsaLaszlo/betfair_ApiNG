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

class NavigationDataFileTest {

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
    void createTree() throws IOException {

        String dataJson = Files.readString(NavigationData.NAVIGATION_DATA_JSON);

        Child rootJson = Operations.GSON.fromJson(dataJson, Child.class);

        Root root = Root.getInstance();

        bejaras(rootJson, root, 0);

//        root.printToConsole(2);

        System.out.println(NavigationData.getSizeOfLists());

        var data = root.getAllData(10);

        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
        Files.writeString(path, data);

        assertEquals(data, Files.readString(path));
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
        else if (o instanceof Race)
            ((Race) o).getMarkets().add(m);
    }

    private void bejaras(Child root, NavigationData o, int deep) {

        NavigationData nd = null;
        switch (root.type) {
            case "EVENT_TYPE" -> nd = new EventType(root.id, root.name);
            case "GROUP" -> {
                if (root.name.equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(root.id, root.name);
                add(o, (Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(root.id, root.name, root.countryCode);
                add(o, (Event) nd);
            }
            case "RACE" -> {
                nd = new Race(root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
                ((EventType) o).getRaces().add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(root.id, root.marketStartTime, root.marketType, root.numberOfWinners, root.name);
                add(o, (Market) nd);
            }
        }

        if ((o = nd) == null) return;
        nd.setMelyseg(deep);


        if (root.children != null) {
            for (Child c : root.children) {
                bejaras(c, o, deep + 1);
            }
        }
    }


//    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @Nested
    @Description("A navigation data-t kiirja fileba es a consolra" + "c:\\temp\\NavigationDataTest.test")
    class navigationDataToFile {

        @Test
        void test() throws IOException {

            String data = Files.readString(NavigationData.NAVIGATION_DATA_JSON);

            Child root = Operations.GSON.fromJson(data, Child.class);

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
