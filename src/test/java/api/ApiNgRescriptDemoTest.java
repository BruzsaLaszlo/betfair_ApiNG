package api;


import com.fasterxml.jackson.core.JsonProcessingException;
import entities.*;
import enums.*;
import exceptions.ApiNgException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 */
class ApiNgRescriptDemoTest {

    private final Operations rescriptOperations = Operations.getInstance();

    @Test
    void demo() {
        assertThatExceptionOfType(ApiNgException.class)
                .isThrownBy(this::start);
    }

    void start() {

        try {

            /**
             * ListEventTypes: Search for the event types and then for the "Horse Racing" in the returned list to finally get
             * the listEventTypeId
             */

            System.out.println("1.(listEventTypes) Get all Event Types...\n");
            List<EventTypeResult> r = rescriptOperations.listEventTypes(MarketFilter.empty());
            System.out.println("2. Extract Event Type Id for Horse Racing...\n");
            Set<String> eventTypeIds = new HashSet<>();
            for (EventTypeResult eventTypeResult : r) {
                if (eventTypeResult.eventType().name().equals("Soccer")) {
                    System.out.println("3. EventTypeId for \"Horse Racing\" is: " + eventTypeResult.eventType().id() + "\n");
                    eventTypeIds.add(eventTypeResult.eventType().id());
                }
            }

            /**
             * ListMarketCatalogue: Get next available horse races, parameters:
             * eventTypeIds : 7 - get all available horse races for event id 7 (horse racing)
             * maxResults: 1 - specify number of results returned (narrowed to 1 to get first race)
             * marketStartTime: specify date (must be in this format: yyyy-mm-ddTHH:MM:SSZ)
             * sort: FIRST_TO_START - specify sort order to first to start race
             */
            System.out.println("4.(listMarketCataloque) Get next horse racing market in the UK...\n");
            TimeRange time = new TimeRange(LocalDateTime.now());

            Set<String> countries = Set.of("GB");

            MarketFilter marketFilter = new MarketFilter()
                    .setEventTypeIds(eventTypeIds)
                    .setMarketStartTime(time)
                    .setMarketCountries(countries);

            Set<MarketProjection> marketProjection = Set.of(MarketProjection.RUNNER_DESCRIPTION);

            int maxResults = 1;

            List<MarketCatalogue> marketCatalogueResult =
                    rescriptOperations.listMarketCatalogue(marketFilter, marketProjection, MarketSort.FIRST_TO_START, maxResults);

            System.out.println("5. Print static marketId, name and runners....\n");
            printMarketCatalogue(marketCatalogueResult.get(0));

            /**
             * ListMarketBook: get list of runners in the market, parameters:
             * marketId:  the market we want to list runners
             *
             */
            System.out.println("6.(listMarketBook) Get volatile info for Market including best 3 exchange prices available...\n");
            String marketIdChosen = marketCatalogueResult.get(0).marketId();

            Set<PriceData> priceData = Set.of(PriceData.EX_BEST_OFFERS);

            List<String> marketIds = List.of(marketIdChosen);

            var params = MarketBookParameters.builder()
                    .marketIds(marketIds)
                    .build();
            List<MarketBook> marketBookReturn = rescriptOperations.listMarketBook(params);

            /**
             * PlaceOrders: we try to place a bet, based on the previous request we provide the following:
             * marketId: the market id
             * selectionId: the runner selection id we want to place the bet on
             * side: BACK - specify side, can be Back or Lay
             * orderType: LIMIT - specify order type
             * size: the size of the bet
             * price: the price of the bet
             * customerRef: 1 - unique reference for a transaction specified by user, must be different for each request
             *
             */


            long selectionId;
            if (!marketBookReturn.isEmpty()) {
                Runner runner = marketBookReturn.get(0).runners().get(0);
                selectionId = runner.selectionId();
                System.out.println("7. Place a bet below minimum stake to prevent the bet actually " +
                        "being placed for marketId: " + marketIdChosen + " with selectionId: " + selectionId + "...\n\n");

                //You can adjust the size and price value in the "API_NG.properties" file
                LimitOrder limitOrder = LimitOrder.builder()
                        .persistenceType(PersistenceType.LAPSE)
                        .price(getPrice())
                        .size(getSize())
                        .build();

                PlaceInstruction instruction = PlaceInstruction.builder()
                        .handicap(0d)
                        .side(Side.BACK)
                        .orderType(OrderType.LIMIT)
                        .limitOrder(limitOrder)
                        .selectionId(selectionId)
                        .build();

                var instructions = List.of(instruction);

                String customerRef = "1";

                PlaceExecutionReport placeBetResult = rescriptOperations.placeOrders(marketIdChosen, instructions, customerRef);

                // Handling the operation result
                if (placeBetResult.status() == ExecutionReportStatus.SUCCESS) {
                    System.out.println("Your bet has been placed!!");
                    System.out.println(placeBetResult.instructionReports());
                } else if (placeBetResult.status() == ExecutionReportStatus.FAILURE) {
                    System.out.println("Your bet has NOT been placed :*( ");
                    System.out.println("The error is: " + placeBetResult.errorCode() + ": " + placeBetResult.errorCode().getMessage());
                }
            } else {
                System.out.println("Sorry, no runners found\n\n");
            }

        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    }

    private static double getPrice() {

        try {
            return 0.01;
        } catch (NumberFormatException e) {
            //returning the default value
            return 1000.0;
        }

    }

    private static double getSize() {
        try {
            return 0.01;
        } catch (NumberFormatException e) {
            //returning the default value
            return 0.01;
        }
    }

    private void printMarketCatalogue(MarketCatalogue mc) {
        System.out.println("Market Name: " + mc.marketName() + "; Id: " + mc.marketId() + "\n");
        List<RunnerCatalog> runners = mc.runners();
        if (runners != null) {
            for (RunnerCatalog rc : runners) {
                System.out.println("Runner Name: " + rc.runnerName() + "; Selection Id: " + rc.selectionId() + "\n");
            }
        }
    }

}

