package bruzsal.betfair.api;

import bruzsal.betfair.entities.*;
import bruzsal.betfair.enums.*;
import bruzsal.betfair.exceptions.ApiNgException;
import bruzsal.betfair.navigation.NavigationData;
import bruzsal.betfair.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static bruzsal.betfair.enums.CountryCodes.HUNGARY;
import static bruzsal.betfair.enums.CountryCodes.UNITED_KINGDOM;
import static bruzsal.betfair.enums.EventTypeIds.SOCCER;
import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {


    Operations operations = Operations.getInstance();

    @Test
    void listEventTypes() throws ApiNgException, JsonProcessingException {

        List<EventTypeResult> list = operations.listEventTypes(MarketFilterBuilder.empty());
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection, MatchProjection matchProjection, boolean includeOverallPosition, boolean partitionMatchedByStrategyRef, Set<String> customerStrategyRefs, String currencyCode, Date matchedSince, Set<String> betIds) throws ApiNgException, JsonProcessingException {
        return operations.listMarketBook(marketIds, priceProjection, orderProjection, matchProjection, includeOverallPosition, partitionMatchedByStrategyRef, customerStrategyRefs, currencyCode, matchedSince, betIds);
    }

    @Test
    void listCountries() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1))
                .build();

        List<CountryCodeResult> list = operations.listCountries(marketFilter);
        list.sort((o1, o2) -> o2.marketCount() < o1.marketCount() ? -1 : 1);
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void listTimeRanges() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1))
                .build();

        List<TimeRangeResult> list = operations.listTimeRanges(marketFilter, TimeGranularity.HOURS);
        list.sort(Comparator.comparing(TimeRangeResult::marketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void listMarketCatalogue() throws ApiNgException, JsonProcessingException {

        var mf = new MarketFilterBuilder()
                .setMarketCountries(HUNGARY)
                .build();
        var mp = Set.of(MarketProjection.EVENT);

        List<MarketCatalogue> mc = operations.listMarketCatalogue(mf, mp, MarketSort.MAXIMUM_AVAILABLE,10);

        assertFalse(mc.isEmpty());

    }

    @Test
    void listMarketTypes() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketCountries(UNITED_KINGDOM)
                .build();

        List<MarketTypeResult> list = operations.listMarketTypes(marketFilter);
        list.sort((o1, o2) -> {
            if (o2.marketCount() < o1.marketCount()) {
                return -1;
            } else if (o2.marketCount() > o1.marketCount()) {
                return 1;
            } else {
                return o1.marketType().compareTo(o2.marketType());
            }
        });
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws ApiNgException, JsonProcessingException {
        return operations.placeOrders(marketId, instructions, customerRef);
    }

    public CancelExecutionReport cancelOrders(String marketId, List<CancelInstruction> instructions, String customerRef) throws ApiNgException, JsonProcessingException {
        return operations.cancelOrders(marketId, instructions, customerRef);
    }

    @Test
    void replaceOrders() throws ApiNgException, JsonProcessingException {

        var ri = new ReplaceInstruction("251188825177", 5);

        assertThrows(ApiNgException.class, () -> {
            ReplaceExecutionReport report = operations.replaceOrders(
                    "1.190116217", List.of(ri), "customerRefReplaceTest");
        });

    }

    @Test
    @Description("csak akkor müxik,ha valós marketId-t és betId-t adok meg")
    void updateOrders() throws ApiNgException, JsonProcessingException {

        UpdateInstruction updateInstruction = new UpdateInstruction("251188825177", PersistenceType.MARKET_ON_CLOSE);

        assertThrows(ApiNgException.class, () -> {
            UpdateExecutionReport updateExecutionReport = operations.updateOrders(
                    "1.190116217", List.of(updateInstruction), "customerRefUpdateTest");
        });

    }

    @Test
    void listCurrentOrders() throws ApiNgException, JsonProcessingException {

        TimeRange timeRange = new TimeRange();
        timeRange.setLFrom(LocalDateTime.now().minusDays(1));
        timeRange.setLTo(LocalDateTime.now());

        CurrentOrdersParametersBuilder copb = new CurrentOrdersParametersBuilder()
                .setPlacedDateRange(timeRange);

        CurrentOrderSummaryReport cosr = operations.listCurrentOrders(copb);

        System.out.println(cosr);

        assertNotNull(cosr);

        assertFalse(cosr.moreAvailable());
    }

    @Test
    void listClearedOrders() throws ApiNgException, JsonProcessingException {
        TimeRange timeRange = new TimeRange();
        timeRange.setLFrom(LocalDateTime.now().minusDays(1));
        timeRange.setLTo(LocalDateTime.now());

        ClearedOrderSummaryParameterBuilder builder = ClearedOrderSummaryParameterBuilder.getDefault();

        ClearedOrderSummaryReport cosr = operations.listClearedOrders(builder);

        assertNotNull(cosr);

    }

    @Test
    void listCompetitions() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilterBuilder()
                .setEventTypeId(SOCCER)
                .setMarketCountries(UNITED_KINGDOM)
                .build();

        List<CompetitionResult> list = operations.listCompetitions(marketFilter);

        list.sort(Comparator.comparing(CompetitionResult::marketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    @Test
    void publiclistEvents() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilterBuilder()
                .setMarketCountries(HUNGARY)
                .build();

        List<EventResult> listEvents = operations.listEvents(marketFilter);

        listEvents.sort(Comparator.comparing(o -> o.event().getOpenDate()));
        listEvents.forEach(System.out::println);

        assertFalse(listEvents.isEmpty());

    }

    public DeveloperApp createDeveloperAppKeys(String appName) throws ApiNgException, JsonProcessingException {
        return operations.createDeveloperAppKeys(appName);
    }


    public HeartbeatReport heartbeat(int preferredTimeoutSeconds) throws ApiNgException, JsonProcessingException {
        return operations.heartbeat(preferredTimeoutSeconds);
    }


    @Test
    void getAccountFunds() throws ApiNgException, JsonProcessingException {

        AccountFundsResponse acr = operations.getAccountFunds();
        assertNotNull(acr);
        System.out.println(acr);
        assertTrue(acr.exposureLimit() < 0);

    }

    @Test
    void getAccountDetails() throws ApiNgException, JsonProcessingException {
        AccountDetailsResponse adr = operations.getAccountDetails();
        assertNotNull(adr);
        System.out.println(adr);
        assertEquals("Laszlo", adr.firstName());
    }

    @Test
    void getDeveloperAppKeys() throws ApiNgException, JsonProcessingException {
        List<DeveloperApp> list = operations.getDeveloperAppKeys();
        assertNotNull(list);
        list.forEach(System.out::println);
        assertEquals("bruzsal", list.get(0).appVersions().get(0).owner());
    }

    @Test
    @Disabled("csak egyéni tesztre")
    void getSessionToken() throws Exception {
        assertTrue(HttpUtil.prop.getProperty("SESSION_TOKEN").endsWith("="));
    }

    @Test
    @Disabled("túl nagy file-t tölt le")
    void getNavigationData() throws JsonProcessingException {
        new NavigationData().updateNavigationData();
        assertFalse(LocalDateTime.now().isEqual(NavigationData.lastUpdateTime));
    }
}