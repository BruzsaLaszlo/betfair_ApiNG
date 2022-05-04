package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mizosoft.methanol.MoreBodyHandlers;
import entities.*;
import enums.*;
import exceptions.ApiNgException;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.ClientProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static enums.CountryCodes.HUNGARY;
import static enums.CountryCodes.UNITED_KINGDOM;
import static enums.EventTypeIds.SOCCER;
import static java.time.LocalDateTime.now;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {


    Operations operations = Operations.getInstance();

    @Test
    void listEventTypes() throws ApiNgException, JsonProcessingException {

        List<EventTypeResult> list = operations.listEventTypes(MarketFilter.empty());
        list.forEach(System.out::println);

        assertThat(list).isNotEmpty();

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

        assertThat(lmb).isNotEmpty();

    }

    @Test
    void listCountries() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(now(), now().plusDays(1));

        List<CountryCodeResult> list = operations.listCountries(marketFilter);
        list.sort(comparing(CountryCodeResult::marketCount));
        list.forEach(System.out::println);

        assertThat(list).isNotEmpty();
    }

    @Test
    void listTimeRanges() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketStartTime(now(), now().plusDays(10));

        List<TimeRangeResult> list = operations.listTimeRanges(marketFilter, TimeGranularity.DAYS);
        list.sort(comparing(TimeRangeResult::marketCount));
        list.forEach(System.out::println);

        assertThat(list).isNotEmpty();

    }

    @Test
    void listMarketCatalogue() throws ApiNgException, JsonProcessingException {

        var mf = new MarketFilter()
                .setMarketCountries(UNITED_KINGDOM)
                .setEventTypeId(SOCCER);
        var mp = Set.of(MarketProjection.RUNNER_DESCRIPTION);

        List<MarketCatalogue> mc = operations.listMarketCatalogue(mf, mp, MarketSort.MAXIMUM_TRADED, 10);

        mc.forEach(System.out::println);

        assertThat(mc).isNotEmpty();

    }

    @Test
    void listMarketTypes() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketCountries(UNITED_KINGDOM);

        List<MarketTypeResult> list = operations.listMarketTypes(marketFilter);
        list.sort(comparing(MarketTypeResult::marketCount).thenComparing(MarketTypeResult::marketType).reversed());

        list.forEach(System.out::println);

        assertThat(list).isNotEmpty();
    }

    @Test
    void placeOrders() {

        var pi = singletonList(
                PlaceInstruction.builder()
                        .orderType(OrderType.LIMIT)
                        .side(Side.BACK)
                        .selectionId(4234234L)
                        .build());

        assertThatExceptionOfType(ApiNgException.class)
                .isThrownBy(() -> operations.placeOrders("1.183689747", pi, "1.183689747L"));

    }

    @Test
    void cancelOrders() {

        var ci = singletonList(new CancelInstruction("231231", 0.1));

        assertThatExceptionOfType(ApiNgException.class)
                .isThrownBy(() -> operations.cancelOrders("1.183689747", ci, "1.183689747L"));

    }

    @Test
    void replaceOrders() {

        var ri = singletonList(new ReplaceInstruction("251188825177", 5d));
        assertThatExceptionOfType(ApiNgException.class)
                .isThrownBy(() -> operations.replaceOrders("1.190116217", ri, "customerRefReplaceTest"));

    }

    @Test
    @Description("csak akkor müxik,ha valós marketId-t és betId-t adok meg")
    void updateOrders() {

        var updateInstruction = singletonList(
                new UpdateInstruction("251188825177", PersistenceType.MARKET_ON_CLOSE));

        assertThatExceptionOfType(ApiNgException.class)
                .isThrownBy(() -> operations.updateOrders("1.190116217", updateInstruction, "customerRefUpdateTest"));

    }

    @Test
    void listCurrentOrders() throws ApiNgException, JsonProcessingException {

        TimeRange timeRange = new TimeRange(now().minusDays(1));

        var params = CurrentOrdersParameters.builder()
                .placedDateRange(timeRange)
                .build();

        CurrentOrderSummaryReport cosr = operations.listCurrentOrders(params);

        System.out.println(cosr);

        assertThat(cosr.moreAvailable()).isFalse();

    }

    @Test
    void listClearedOrders() throws ApiNgException, JsonProcessingException {

        TimeRange timeRange = new TimeRange(
                now().minusDays(1),
                now());

        var params = ClearedOrderSummaryParameters.builder()
                .settledDateRange(timeRange)
                .betStatus(BetStatus.SETTLED)
                .build();

        ClearedOrderSummaryReport cosr = operations.listClearedOrders(params);

        assertThat(cosr).isNotNull();

    }

    @Test
    void listCompetitions() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setEventTypeId(SOCCER)
                .setMarketCountries(UNITED_KINGDOM);

        List<CompetitionResult> list = operations.listCompetitions(marketFilter);

        list.sort(comparing(CompetitionResult::marketCount));
        list.forEach(System.out::println);

        assertThat(list).isNotEmpty();

    }

    @Test
    void listEvents() throws ApiNgException, JsonProcessingException {

        var marketFilter = new MarketFilter()
                .setMarketCountries(HUNGARY);

        List<EventResult> listEvents = operations.listEvents(marketFilter);

        listEvents.sort(comparing(o -> o.event().openDate()));
        listEvents.forEach(System.out::println);

        assertThat(listEvents).isNotEmpty();

    }


    @Test
    void heartbeat() throws ApiNgException, JsonProcessingException {

        HeartbeatReport hbr = operations.heartbeat(10);
        System.out.println(hbr);

        assertEquals(10, hbr.actualTimeoutSeconds());

        hbr = operations.heartbeat(0);
        System.out.println(hbr);

        assertThat(hbr.actualTimeoutSeconds()).isZero();

    }


    @Test
    void getAccountFunds() throws ApiNgException, JsonProcessingException {

        AccountFundsResponse acr = operations.getAccountFunds();
        assertNotNull(acr);
        System.out.println(acr);

        assertThat(acr.exposureLimit()).isNegative();

    }

    @Test
    void getAccountDetails() throws ApiNgException, JsonProcessingException {

        AccountDetailsResponse adr = operations.getAccountDetails();

        System.out.println(adr);

        assertThat(adr.firstName()).isEqualTo("Laszlo");

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
        assertTrue(ClientProperties.sessionToken().endsWith("="));
    }


    @Test
    @Disabled("nem oparion test")
    void testObjectMapper() throws JsonProcessingException {

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
    @Disabled("nem oparion test")
    void testObjectMapper2() throws JsonProcessingException {

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
        System.out.println(list);


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
    @Disabled("http test")
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