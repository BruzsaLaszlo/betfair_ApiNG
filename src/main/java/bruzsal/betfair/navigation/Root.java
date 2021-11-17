package bruzsal.betfair.navigation;

import java.util.Arrays;
import java.util.List;

public final class Root extends NavigationData {

    public static Root getInstance() {
        return new Root();
    }

    private Root() {
        super("0", "ROOT");
    }

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Arrays.asList(allEvenType);
    }


    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
