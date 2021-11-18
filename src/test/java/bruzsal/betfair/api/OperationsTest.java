package bruzsal.betfair.api;

import bruzsal.betfair.entities.*;
import bruzsal.betfair.enums.*;
import bruzsal.betfair.exceptions.APINGException;
import bruzsal.betfair.navigation.NavigationData;
import bruzsal.betfair.navigation.Root;
import bruzsal.betfair.util.HttpUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

import static bruzsal.betfair.enums.CountryCodes.HUNGARY;
import static bruzsal.betfair.enums.EventTypeIds.SOCCER;
import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {


    Operations operations = Operations.getInstance();

    @Test
    void listEventTypes() throws APINGException {

        List<EventTypeResult> list = operations.listEventTypes(MarketFilterBuilder.empty());
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection, MatchProjection matchProjection, boolean includeOverallPosition, boolean partitionMatchedByStrategyRef, Set<String> customerStrategyRefs, String currencyCode, Date matchedSince, Set<String> betIds) throws APINGException {
        return operations.listMarketBook(marketIds, priceProjection, orderProjection, matchProjection, includeOverallPosition, partitionMatchedByStrategyRef, customerStrategyRefs, currencyCode, matchedSince, betIds);
    }

    @Test
    void listCountries() throws APINGException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1))
                .build();

        List<CountryCodeResult> list = operations.listCountries(marketFilter);
        list.sort((o1, o2) -> o2.getMarketCount() < o1.getMarketCount() ? -1 : 1);
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    public List<VenueResult> listVenues(MarketFilter filter) throws APINGException {
        return operations.listVenues(filter);
    }

    @Test
    void listTimeRanges() throws APINGException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1))
                .build();

        List<TimeRangeResult> list = operations.listTimeRanges(marketFilter, TimeGranularity.HOURS);
        list.sort(Comparator.comparing(TimeRangeResult::getMarketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection, MarketSort sort, int maxResult) throws APINGException {
        return operations.listMarketCatalogue(filter, marketProjection, sort, maxResult);
    }

    @Test
    void listMarketTypes() throws APINGException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketCountries(HUNGARY)
                .build();

        List<MarketTypeResult> list =  operations.listMarketTypes(marketFilter);
        list.sort((o1, o2) -> o2.getMarketCount() < o1.getMarketCount() ? -1 : 1);
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws APINGException {
        return operations.placeOrders(marketId, instructions, customerRef);
    }

    public CancelExecutionReport cancelOrders(String marketId, List<CancelInstruction> instructions, String customerRef) throws APINGException {
        return operations.cancelOrders(marketId, instructions, customerRef);
    }

    public ReplaceExecutionReport replaceOrders(String marketId, List<ReplaceInstruction> instructions, String customerRef) throws APINGException {
        return operations.replaceOrders(marketId, instructions, customerRef);
    }

    public UpdateExecutionReport updateOrders(String marketId, List<UpdateInstruction> instructions, String customerRef) throws APINGException {
        return operations.updateOrders(marketId, instructions, customerRef);
    }

    public CurrentOrderSummaryReport listCurrentOrders(Set<String> betIds, Set<String> marketIds, OrderProjection orderProjection, TimeRange placedDateRange, OrderBy orderBy, SortDir sortDir, int fromRecord, int recordCount) throws APINGException {
        return operations.listCurrentOrders(betIds, marketIds, orderProjection, placedDateRange, orderBy, sortDir, fromRecord, recordCount);
    }

    public ClearedOrderSummaryReport listClearedOrders(BetStatus betStatus, Set<String> eventTypeIds, Set<String> eventIds, Set<String> marketIds, Set<String> runnerIds, Set<String> betIds, Side side, TimeRange settledDateRange, GroupBy groupBy, boolean includeItemDescription, String locale, int fromRecord, int recordCount) throws APINGException {
        return operations.listClearedOrders(betStatus, eventTypeIds, eventIds, marketIds, runnerIds, betIds, side, settledDateRange, groupBy, includeItemDescription, locale, fromRecord, recordCount);
    }

    public ClearedOrderSummaryReport listClearedOrders(BetStatus betStatus, TimeRange settledDateRange, GroupBy groupBy, Boolean includeItemDescription, Integer fromRecord, Integer recordCount) throws APINGException {
        return operations.listClearedOrders(betStatus, settledDateRange, groupBy, includeItemDescription, fromRecord, recordCount);
    }

    @Test
    void listCompetitions() throws APINGException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketCountries(HUNGARY)
                .build();

        List<CompetitionResult> list = operations.listCompetitions(marketFilter);

        list.sort(Comparator.comparing(CompetitionResult::getMarketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    @Test
    void getCountryCodes() throws IOException {
        List<String> listOfCodes = Files.readAllLines(Path.of("c:\\Users\\Laci\\IdeaProjects\\Betfair_APING\\iso_code.txt"));
        for (String s : listOfCodes) {
            var sa = s.split(";");
            sa[0] = sa[0].split(",")[0];
            sa[0] = sa[0].replaceAll("([(].+[)]|[-])", "");
            sa[0] = sa[0].replaceAll("\\s+", "_");
            System.out.printf("%s(\"%s\"),%n", sa[0], sa[1]);
        }
    }

    @Test
    void publiclistEvents() throws APINGException {

        var marketFilter = new MarketFilterBuilder()
                .setMarketCountries(HUNGARY)
                .build();

        List<EventResult> listEvents = operations.listEvents(marketFilter);

        listEvents.sort(Comparator.comparing(o -> o.getEvent().getOpenDate()));

        listEvents.forEach(System.out::println);

    }

    public DeveloperApp createDeveloperAppKeys(String appName) throws APINGException {
        return operations.createDeveloperAppKeys(appName);
    }

    public AccountFundsResponse getAccountFunds() throws APINGException {
        return operations.getAccountFunds();
    }

    public AccountDetailsResponse getAccountDetails() throws APINGException {
        return operations.getAccountDetails();
    }

    public HeartbeatReport heartbeat(int preferredTimeoutSeconds) throws APINGException {
        return operations.heartbeat(preferredTimeoutSeconds);
    }

    @Test
    @Disabled
    void getSessionToken() throws Exception {
        assertTrue(HttpUtil.prop.getProperty("SESSION_TOKEN").endsWith("="));
    }

    @Test
    void makeRequest() throws APINGException {


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
    void accountFunds() throws APINGException {
        AccountFundsResponse acr = operations.getAccountFunds();
        assertNotNull(acr);
        assertTrue(acr.getAvailableToBetBalance() > 0);
    }

    @Test
    void accountDetails() throws APINGException {
        AccountDetailsResponse adr = operations.getAccountDetails();
        assertEquals("Laszlo", adr.getFirstName());
    }

    @Test
    void getDeveloperAppKeys() throws APINGException {
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