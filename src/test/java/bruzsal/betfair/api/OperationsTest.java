package bruzsal.betfair.api;

import bruzsal.betfair.entities.*;
import bruzsal.betfair.enums.*;
import bruzsal.betfair.exceptions.ApiNgException;
import bruzsal.betfair.navigation.NavigationData;
import bruzsal.betfair.util.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mizosoft.methanol.MoreBodyHandlers;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static bruzsal.betfair.enums.CountryCodes.HUNGARY;
import static bruzsal.betfair.enums.CountryCodes.UNITED_KINGDOM;
import static bruzsal.betfair.enums.EventTypeIds.SOCCER;
import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {


    Operations operations = Operations.getInstance();

    @Test
    void listEventTypes() throws ApiNgException, JsonProcessingException {

        List<EventTypeResult> list = operations.listEventTypes(MarketFilter.empty());
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    @Test
    void listMarketBook() throws ApiNgException, JsonProcessingException {

        var mf = new MarketFilter()
                .setMarketCountries(UNITED_KINGDOM)
                .setEventTypeId(SOCCER);
        var mp = Set.of(MarketProjection.RUNNER_DESCRIPTION);

        List<MarketCatalogue> mc = operations.listMarketCatalogue(mf, mp, MarketSort.MAXIMUM_TRADED, 1);

        var params = MarketBookParameters.builder()
                .marketIds(List.of(mc.get(0).marketId()))
                .build();

        List<MarketBook> lmb = operations.listMarketBook(params);

        lmb.forEach(System.out::println);

        assertFalse(lmb.isEmpty());

    }

    @Test
    void listCountries() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        List<CountryCodeResult> list = operations.listCountries(marketFilter);
        list.sort(comparing(CountryCodeResult::marketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void listTimeRanges() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(LocalDateTime.now(), LocalDateTime.now().plusDays(10));

        List<TimeRangeResult> list = operations.listTimeRanges(marketFilter, TimeGranularity.DAYS);
        list.sort(comparing(TimeRangeResult::marketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    @Test
    void listMarketCatalogue() throws ApiNgException, JsonProcessingException {

        var mf = new MarketFilter()
                .setMarketCountries(UNITED_KINGDOM)
                .setEventTypeId(SOCCER);
        var mp = Set.of(MarketProjection.RUNNER_DESCRIPTION);

        List<MarketCatalogue> mc = operations.listMarketCatalogue(mf, mp, MarketSort.MAXIMUM_TRADED, 10);

        mc.forEach(System.out::println);

        assertFalse(mc.isEmpty());

    }

    @Test
    void listMarketTypes() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketCountries(UNITED_KINGDOM);

        List<MarketTypeResult> list = operations.listMarketTypes(marketFilter);
        list.sort(comparing(MarketTypeResult::marketCount).thenComparing(MarketTypeResult::marketType).reversed());

        list.forEach(System.out::println);

        assertTrue(list.size() > 0);
    }

    @Test
    void placeOrders() {

        PlaceInstruction pi = PlaceInstruction.builder()
                .orderType(OrderType.LIMIT)
                .side(Side.BACK)
                .selectionId(4234234L)
                .build();

        assertThrows(ApiNgException.class, () -> operations.placeOrders("1.183689747", List.of(pi), "1.183689747L"));

    }

    @Test
    void cancelOrders() {

        CancelInstruction ci = new CancelInstruction("231231", 0.1);

        assertThrows(ApiNgException.class, () -> operations.cancelOrders("1.183689747", List.of(ci), "1.183689747L"));

    }

    @Test
    void replaceOrders() {

        var ri = new ReplaceInstruction("251188825177", 5d);

        assertThrows(ApiNgException.class, () ->
                operations.replaceOrders("1.190116217", List.of(ri), "customerRefReplaceTest"));

    }

    @Test
    @Description("csak akkor müxik,ha valós marketId-t és betId-t adok meg")
    void updateOrders() {

        UpdateInstruction updateInstruction = new UpdateInstruction("251188825177", PersistenceType.MARKET_ON_CLOSE);

        assertThrows(ApiNgException.class, () ->
                operations.updateOrders("1.190116217", List.of(updateInstruction), "customerRefUpdateTest"));

    }

    @Test
    void listCurrentOrders() throws ApiNgException, JsonProcessingException {

        TimeRange timeRange = new TimeRange(
                LocalDateTime.now().minusDays(1)
        );

        var params = CurrentOrdersParameters.builder()
                .placedDateRange(timeRange)
                .build();

        CurrentOrderSummaryReport cosr = operations.listCurrentOrders(params);

        System.out.println(cosr);

        assertNotNull(cosr);

        assertFalse(cosr.moreAvailable());
    }

    @Test
    void listClearedOrders() throws ApiNgException, JsonProcessingException {

        TimeRange timeRange = new TimeRange(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );

        var params = new ClearedOrderSummaryParameterBuilder()
                .setSettledDateRange(timeRange)
                .setBetStatus(BetStatus.SETTLED)
                .build();

        ClearedOrderSummaryReport cosr = operations.listClearedOrders(params);

        assertNotNull(cosr);

    }

    @Test
    void listCompetitions() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketCountries(UNITED_KINGDOM);

        List<CompetitionResult> list = operations.listCompetitions(marketFilter);

        list.sort(comparing(CompetitionResult::marketCount));
        list.forEach(System.out::println);

        assertTrue(list.size() > 0);

    }

    @Test
    void listEvents() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setMarketCountries(HUNGARY);

        List<EventResult> listEvents = operations.listEvents(marketFilter);

        listEvents.sort(comparing(o -> o.event().getOpenDate()));
        listEvents.forEach(System.out::println);

        assertFalse(listEvents.isEmpty());

    }


    @Test
    void heartbeat() throws ApiNgException, JsonProcessingException {

        HeartbeatReport hbr = operations.heartbeat(10);
        System.out.println(hbr);

        assertEquals(10, hbr.actualTimeoutSeconds());

        hbr = operations.heartbeat(0);
        System.out.println(hbr);

        assertEquals(0, hbr.actualTimeoutSeconds());

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
    void getSessionToken() {
        assertTrue(Properties.sessionToken().endsWith("="));
    }

    @Test
    @Disabled("túl nagy file-t tölt le")
    void getNavigationData() throws JsonProcessingException {

        new NavigationData().updateNavigationData();
        assertFalse(LocalDateTime.now().isEqual(NavigationData.lastUpdateTime));

    }

    @Test
    void json() throws JsonProcessingException {

        String json = """
                {
                  "sessionToken":"3W7G9J+7dvc1X6BdZzAIwzRp/wOmRaydQ1H/KZ9aj24=",
                  "loginStatus":"SUCCESS",
                  "list":["Ford", "BMW", "Fiat"]
                }
                """;

        record St(String sessionToken, String loginStatus, List<String> list) {
        }

        ObjectMapper mapper = new ObjectMapper();
        St st = mapper.readValue(json, St.class);
        System.out.println(st.list);

        assertEquals("SUCCESS", st.loginStatus);

    }

    @Test
    void json2() throws JsonProcessingException {

        String json = """
                [{
                    "list":["Ford", "BMW", "Fiat"]
                }]
                """;

        String jsonApp = """
                [
                  {
                	"appName":"brzslck",
                	"appId":6222,
                	"appVersions":[
                	                {
                	                    "owner":"bruzsal",
                					    "versionId":5156,"version":"1.0-DELAY",
                					    "applicationKey":"PWXyR1ihpVYw4dFe",
                					    "delayData":true,"subscriptionRequired":true,
                					    "ownerManaged":false,"active":true
                					},
                				    {
                				        "owner":"bruzsal",
                					    "versionId":5155,"version":"1.0",
                					    "applicationKey":"MB8aoL9masmFkTCF",
                					    "delayData":false,
                					    "subscriptionRequired":true,
                					    "ownerManaged":false,"active":false
                				    }
                				   ]
                	}
                ]
                """;

        ObjectMapper mapper = new ObjectMapper();
        List<String> list = mapper.readValue(json, List.class);


        record DeveloperAppVersion(String owner, long versionId, String version, String applicationKey,
                                   boolean delayData,
                                   boolean subscriptionRequired, boolean ownerManaged, boolean active) {
        }

        record DeveloperApp(String appName, long appId, List<DeveloperAppVersion> appVersions) {
        }


        List<DeveloperApp> listApp = mapper.readValue(jsonApp, new TypeReference<>() {
        });
        System.out.println(listApp);

        assertTrue(listApp.get(0).appVersions.get(0).subscriptionRequired);
    }


    @Test
    void htttp() throws IOException, InterruptedException, URISyntaxException {
        String url = "https://api.betfair.com/exchange/account/rest/v1.0/getAccountDetails/";

        HttpRequest requestIndex = HttpRequest.newBuilder()
                .uri(new URI("https://index.hu"))
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .headers("key1", "value1", "key2", "value2")
                .header("key1", "value1")
                .header("key2", "value2")
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/post"))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString("Sample request body"))
                .build();

        byte[] sampleData = "Sample request body".getBytes();
        HttpRequest requestInput = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/post"))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers
                        .ofInputStream(() -> new ByteArrayInputStream(sampleData)))
                .build();

        HttpRequest requestByte = HttpRequest.newBuilder()
                .uri(new URI("https://postman-echo.com/post"))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofByteArray(sampleData))
                .build();

//        HttpRequest requestFile = HttpRequest.newBuilder()
//                .uri(new URI("https://postman-echo.com/post"))
//                .headers("Content-Type", "text/plain;charset=UTF-8")
//                .POST(HttpRequest.BodyPublishers.fromFile(
//                        Paths.get("src/test/resources/sample.txt")))
//                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(requestIndex, MoreBodyHandlers.decoding(HttpResponse.BodyHandlers.ofString()));

        HttpHeaders responseHeaders = response.headers();

//        Methanol

        System.out.println(responseHeaders.toString());
        System.out.println(response);
        System.out.println(response.body());

    }

}