package bruzsal.betfair.api;

import bruzsal.betfair.entities.*;
import bruzsal.betfair.navigation.NavigationData;
import com.betfair.aping.entities.*;
import bruzsal.betfair.exceptions.ApiNgException;
import bruzsal.betfair.navigation.Root;
import bruzsal.betfair.util.HttpUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {


    Operations operations = Operations.getInstance();



    @Test
    @Disabled
    void getSessionToken() throws Exception {
        assertTrue(HttpUtil.prop.getProperty("SESSION_TOKEN").endsWith("="));
    }

    @Test
    void makeRequest() throws ApiNgException {


        MarketFilter marketFilter;
        marketFilter = new MarketFilter();
        Set<String> eventTypeIds = new HashSet<>();

        List<EventTypeResult> r = operations.listEventTypes(marketFilter);

        for (EventTypeResult eventTypeResult : r) {
            if (eventTypeResult.getEventType().getName().equals("Soccer")) {
                System.out.println("3. EventTypeId for \"Horse Racing\" is: " + eventTypeResult.getEventType().getId() + "\n");
                eventTypeIds.add(eventTypeResult.getEventType().getId());
            }
        }

        assertFalse(eventTypeIds.isEmpty());

    }

    @Test
    void accountFunds() throws ApiNgException {
        AccountFundsResponse acr = operations.getAccountFunds();
        assertNotNull(acr);
        assertTrue(acr.getAvailableToBetBalance() > 0);
    }

    @Test
    void accountDetails() throws ApiNgException {
        AccountDetailsResponse adr = operations.getAccountDetails();
        assertEquals("Laszlo", adr.getFirstName());
    }

    @Test
    void getDeveloperAppKeys() throws ApiNgException {
        List<DeveloperApp> da = operations.getDeveloperAppKeys();
        assertEquals("bruzsal", da.get(0).getAppVersions().get(0).getOwner());
    }

    @Test
    @Disabled
    void getNavigationData() throws IOException {
//        String data = HttpUtil.getNavigationData();
        Root.getInstance().updateNavigationData();
        assertFalse(LocalDateTime.now().isEqual(NavigationData.lastUpdateTime));
    }
}