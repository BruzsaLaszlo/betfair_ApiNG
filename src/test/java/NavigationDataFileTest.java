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


    class Root {

        private String name;

        private String id;

        private String type;

        private List<Child> children;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public List<Child> getChildren() {
            return children;
        }
    }

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

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getExchangeId() {
            return exchangeId;
        }

        public String getMarketType() {
            return marketType;
        }

        public Date getMarketStartTime() {
            return marketStartTime;
        }

        public Object getNumberOfWinners() {
            return numberOfWinners;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public List<Child> getChildren() {
            return children;
        }

        public String getVenue() {
            return venue;
        }

        public Date getStartTime() {
            return startTime;
        }

        public String getRaceNumber() {
            return raceNumber;
        }
    }

    Operations operations = Operations.getInstance();

    @Test
    void navigationDataToObject() throws IOException {

        String data = Files.readString(Path.of("src/main/resources/NavigationData.json"));


        Root root = Operations.gson.fromJson(data, Root.class);

        assertNotNull(root);
        assertFalse(root.getChildren().isEmpty());

        for (Child e : root.getChildren()) {
            System.out.println(e.getId() + "  " + e.getName() + "  " + e.getType());
            for (Child ce : e.getChildren()) {
                System.out.println("       " + e.getId() + "  " + e.getName() + "  " + e.getType());
                for (Child ce2 : e.getChildren()) {
                    System.out.println("       " + e.getId() + "  " + e.getName() + "  " + e.getType());
                }
            }
        }


    }


}
