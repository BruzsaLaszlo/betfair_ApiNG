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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

        List<MarketTypeResult> list = operations.listMarketTypes(marketFilter);
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

    @Test
    void listCurrentOrders() throws APINGException {

        TimeRange timeRange = new TimeRange();
        timeRange.setFrom(LocalDateTime.now().minusDays(1));
        timeRange.setTo(LocalDateTime.now());

        CurrentOrdersParametersBuilder copb = CurrentOrdersParametersBuilder.getDefault();

        CurrentOrderSummaryReport cosr =  operations.listCurrentOrders(copb);

        System.out.println(cosr);

        assertNotNull(cosr);

        assertFalse(cosr.isMoreAvailable());
    }

    public ClearedOrderSummaryReport listClearedOrders(BetStatus betStatus, Set<String> eventTypeIds, Set<String> eventIds, Set<String> marketIds, Set<String> runnerIds, Set<String> betIds, Side side, TimeRange settledDateRange, GroupBy groupBy, boolean includeItemDescription, String locale, int fromRecord, int recordCount) throws APINGException {
        return operations.listClearedOrders(betStatus, eventTypeIds, eventIds, marketIds, runnerIds, betIds, side, settledDateRange, groupBy, includeItemDescription, locale, fromRecord, recordCount);
    }

    @Test
    void listClearedOrders() throws APINGException {
        TimeRange timeRange = new TimeRange();
        timeRange.setFrom(LocalDateTime.now().minusDays(1));
        timeRange.setTo(LocalDateTime.now());

        ClearedOrderSummaryReport cosr =  operations.listClearedOrders(
                BetStatus.CANCELLED, timeRange, GroupBy.MARKET, true, 0, 1000);

        assertNotNull(cosr);

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


    public HeartbeatReport heartbeat(int preferredTimeoutSeconds) throws APINGException {
        return operations.heartbeat(preferredTimeoutSeconds);
    }

    @Test
    void getAccountFunds() throws APINGException {
        AccountFundsResponse acr = operations.getAccountFunds();
        assertNotNull(acr);
        System.out.println(acr);
        assertTrue(acr.getExposureLimit() < 0);
    }

    @Test
    void getAccountDetails() throws APINGException {
        AccountDetailsResponse adr = operations.getAccountDetails();
        assertNotNull(adr);
        System.out.println(adr);
        assertEquals("Laszlo", adr.getFirstName());
    }

    @Test
    void getDeveloperAppKeys() throws APINGException {
        List<DeveloperApp> list = operations.getDeveloperAppKeys();
        assertNotNull(list);
        list.forEach(System.out::println);
        assertEquals("bruzsal", list.get(0).getAppVersions().get(0).getOwner());
    }

    @Test
    @Disabled("")
    void getSessionToken() throws Exception {
        assertTrue(HttpUtil.prop.getProperty("SESSION_TOKEN").endsWith("="));
    }

    @Test
    @Disabled("túl nagy file-t tölt le")
    void getNavigationData() throws IOException {
//        String data = HttpUtil.getNavigationData();
        Root.getInstance().updateNavigationData();
        assertFalse(LocalDateTime.now().isEqual(NavigationData.lastUpdateTime));
    }
}