package bruzsal.betfair.api;

import bruzsal.betfair.navigation.*;
import bruzsal.betfair.navigation.NavigationData.rawChild;
import jdk.jfr.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static bruzsal.betfair.navigation.NavigationDataBase.*;
import static org.junit.jupiter.api.Assertions.*;

class NavigationDataFileTest {


//    @Test
//    void createTree() throws IOException {
//
//        String dataJson = Files.readString(NavigationData.NAVIGATION_DATA_JSON);
//
//        Child rootJson = Operations.GSON.fromJson(dataJson, Child.class);
//
//        Root root = Root.getInstance();
//
//        bejaras(rootJson, root, 0);
//
////        root.printToConsole(2);
//
//        System.out.println(NavigationDataBase.getSizeOfLists());
//
////        var data = root.getAllData(10);
//
////        Path path = Path.of("c:\\temp\\NavigationDataTest.test");
////        Files.writeString(path, data);
//
////        assertEquals(data, Files.readString(path));
////        bejaras(o, 0);
//
//    }
//
//    private void add(Child o, Group g) {
//        switch (o) {
//            case EventType eventType -> eventType.getGroups().add(g);
//            case Group group -> group.getGroups().add(g);
//            case Event event -> event.getGroups().add(g);
//            case null, default -> throw new IllegalStateException("Az EventType nem lehet mas");
//        }
//    }
//
//    private void add(Child o, Event e) {
//        switch (o) {
//            case EventType eventType -> eventType.getEvents().add(e);
//            case Group group -> group.getEvents().add(e);
//            case Event event -> event.getEvents().add(e);
//            case null, default -> throw new IllegalStateException("Az Event nem lehet mas");
//        }
//    }
//
//    private void add(Child o, Market m) {
//        switch (o) {
//            case Event event -> event.getMarkets().add(m);
//            case Race race -> race.getMarkets().add(m);
//            case null, default -> throw new IllegalStateException("Az Market nem lehet mas");
//        }
//    }
//
//    private void bejaras(rawChild root, Child o, int depth) {
//
//        Child nd = null;
//        switch (root.type) {
//            case "EVENT_TYPE" -> {
//                if (root.id.equals("7") && root.name.equals("Horse Racing") && HORSE_RACING_OFF)
//                    break;
//                nd = new EventType(root.id, root.name);
//                allEvenType.add((EventType) nd);
//            }
//            case "GROUP" -> {
//                if (root.name.equals("ROOT")) {
//                    nd = o;
//                    break;
//                }
//                nd = new Group(root.id, root.name);
//                add(o, (Group) nd);
//                allGroup.add((Group) nd);
//            }
//            case "EVENT" -> {
//                nd = new Event(root.id, root.name, root.countryCode);
//                add(o, (Event) nd);
//                allEvent.add((Event) nd);
//            }
//            case "RACE" -> {
//                if (HORSE_RACING_OFF)
//                    break;
//                nd = new Race(root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
//                ((EventType) o).getRaces().add((Race) nd);
//                allRace.add((Race) nd);
//            }
//            case "MARKET" -> {
//                nd = new Market(root.id, root.marketStartTime, root.marketType, root.numberOfWinners, root.name);
//                add(o, (Market) nd);
//                allMarket.add((Market) nd);
//            }
//            default -> throw new IllegalStateException("A root(child) nem lehet mas");
//        }
//
//        if (nd != null)
//            nd.parent = o;
//        if ((o = nd) == null) return;
//        nd.setDepth(depth);
//
//
//        if (root.children != null)
//            for (rawChild c : root.children)
//                bejaras(c, o, depth + 1);
//
//    }


//    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//    @Nested
//    @Description("A navigation data-t kiirja fileba es a consolra" + "c:\\temp\\NavigationDataTest.test")
//    class navigationDataToFile {
//
//        @Test
//        void test() throws IOException {
//
//            String data = Files.readString(NavigationData.NAVIGATION_DATA_JSON);
//
//            Child root = Operations.GSON.fromJson(data, Child.class);
//
//            assertNotNull(root);
//            assertFalse(root.children.isEmpty());
//
//
//            bejaras(root, 0);
//
//            System.out.println(sb);
//
//            Files.writeString(Path.of("c:\\temp\\NavigationDataTest.test"), sb.toString());
//
//        }
//
//        static final String[] spaces = {
//                "",
//                "    ",
//                "         ",
//                "              ",
//                "                   ",
//                "                        ",
//                "                             ",
//        };
//
//        StringBuilder sb = new StringBuilder();
//        int count = 0;
//
//        private void bejaras(Child root, int i) {
//            if (root.children != null) {
//                for (Child c : root.children) {
//                    count++;
//                    sb.append(spaces[i]).append(c.name).append("\n");
//                    bejaras(c, i + 1);
//                }
//            }
//        }
//
//    }

}
