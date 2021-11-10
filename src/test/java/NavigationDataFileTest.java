import com.betfair.aping.api.Operations;
import com.betfair.aping.navigation.*;
import com.betfair.aping.util.HttpUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        String data = Files.readString(Path.of(HttpUtil.prop.getProperty("NAVIGATION_DATA")));

        Child root = Operations.gson.fromJson(data, Child.class);

        Root o = new Root();


        for (Child c : root.children) {
            EventType et = new EventType(c.id, c.name);
            o.children.add(et);
            bejaras(c, et);
        }

        bejaras(o,0);

    }

    private void add(Object o, Group g) {
        if (o instanceof EventType)
            ((EventType) o).getGroups().add(g);
        else if (o instanceof Group)
            ((Group) o).getGroups().add(g);
        else if (o instanceof Event)
            ((Event) o).getGroups().add(g);
    }

    private void add(Object o, Event e) {
        if (o instanceof EventType)
            ((EventType) o).getEvents().add(e);
        else if (o instanceof Group)
            ((Group) o).getEvents().add(e);
        else if (o instanceof Event)
            ((Event) o).getEvents().add(e);
    }

    private void add(Object o, Market m) {
        if (o instanceof Event)
            ((Event) o).getMarkets().add(m);
        else ((Race) o).getMarkets().add(m);
    }

    private void bejaras(Child root, Object o) {
//        spaces = space();

        switch (root.type) {
            case "EVENT_TYPE":
                break;
            case "GROUP":
                Group g = new Group(root.id, root.name);
                add(o, g);
                o = g;
                break;
            case "EVENT":
                Event e = new Event(root.id, root.name, root.countryCode);
                add(o, e);
                o = e;
                break;
            case "RACE":
                Race r = new Race(root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
                ((EventType) o).getRaces().add(r);
                o = r;
                break;
            case "MARKET":
                Market m = new Market(root.exchangeId, root.id, root.marketStartTime, root.marketType, root.numberOfWinners, root.name);
                add(o,m);
                o = m;
                break;
        }

        if (root.children != null) {
            for (Child c : root.children) {
                bejaras(c, o);
            }
        }
    }

    private void bejaras(Object root, int i) {
//        if (root.children != null) {
//            for (Child c : root.children) {
//                count++;
//                sb.append(spaces[i]).append(c.name).append("\n");
//                bejaras(c, i + 1);
//            }
//        }
    }

    @Test
    void navigationDataToObject() throws IOException {

        String data = Files.readString(Path.of(HttpUtil.prop.getProperty("NAVIGATION_DATA")));

        Child root = Operations.gson.fromJson(data, Child.class);

        assertNotNull(root);
        assertFalse(root.children.isEmpty());


        bejaras(root, 0);

        System.out.println(sb);

        Files.writeString(Path.of("src/main/resources/NavigationDataTest.test"), sb.toString());

    }

    int count = 0;


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
