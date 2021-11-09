import com.betfair.aping.api.Operations;
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
        public Object numberOfWinners;
        public String countryCode;
        public List<Child> children;
        public String venue;
        public Date startTime;
        public String raceNumber;

    }

    Operations operations = Operations.getInstance();

    @Test
    void navigationDataToObject() throws IOException {

        String data = Files.readString(Path.of("src/main/resources/NavigationData.json"));

        Child root = Operations.gson.fromJson(data, Child.class);

        assertNotNull(root);
        assertFalse(root.children.isEmpty());


        bejaras(root,0);

        System.out.println(sb);

    }

    int count = 0;

    //    private String space() {
//        StringBuilder s = new StringBuilder();
//        for (int i = 0; i < count * 8; i++) {
//            s.append(" ");
//        }
//        return s.toString();
//    }
//
//    String spaces = space();
//
    String[] spaces = {
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
//        spaces = space();
        if (root.children != null) {
            for (Child c : root.children) {
                count++;
                sb.append(spaces[i]).append(c.name).append("\n");
                bejaras(c, i + 1);
            }
        }
    }


}
