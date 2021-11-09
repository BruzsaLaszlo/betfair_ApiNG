package com.betfair.aping.api;

import com.betfair.aping.entities.AccountFundsResponse;
import com.betfair.aping.entities.DeveloperApp;
import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.exceptions.ApiNgException;
import com.betfair.aping.util.SessionTokenGetter;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {


    Operations operations = Operations.getInstance();

    @Test
    void getSessionToken() throws Exception {
        assertTrue(SessionTokenGetter.getSessionToken().endsWith("="));
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
    void getDeveloperAppKeys() throws ApiNgException {
        List<DeveloperApp> da = operations.getDeveloperAppKeys();
        assertEquals("bruzsal", da.get(0).getAppVersions().get(0).getOwner());
    }

}