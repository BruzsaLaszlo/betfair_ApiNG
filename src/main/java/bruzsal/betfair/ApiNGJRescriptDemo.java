package bruzsal.betfair;

import bruzsal.betfair.api.Operations;
import bruzsal.betfair.entities.*;
import bruzsal.betfair.enums.*;
import bruzsal.betfair.exceptions.ApiNgException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 */
public class ApiNGJRescriptDemo {

    private final Operations rescriptOperations = Operations.getInstance();

    public void start() {

        try {

            /**
             * ListEventTypes: Search for the event types and then for the "Horse Racing" in the returned list to finally get
             * the listEventTypeId
             */
            MarketFilter marketFilter;
            marketFilter = new MarketFilter();
            Set<String> eventTypeIds = new HashSet<>();

            System.out.println("1.(listEventTypes) Get all Event Types...\n");
            List<EventTypeResult> r = rescriptOperations.listEventTypes(marketFilter);
            System.out.println("2. Extract Event Type Id for Horse Racing...\n");
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
            TimeRange time = new TimeRange();
            time.setFrom(new Date());

            Set<String> countries = new HashSet<>();
            countries.add("GB");

            marketFilter = new MarketFilter();
            marketFilter.setEventTypeIds(eventTypeIds);
            marketFilter.setMarketStartTime(time);
            marketFilter.setMarketCountries(countries);

            Set<MarketProjection> marketProjection = new HashSet<>();
            marketProjection.add(MarketProjection.RUNNER_DESCRIPTION);

            int maxResults = 1;

            List<MarketCatalogue> marketCatalogueResult = rescriptOperations.listMarketCatalogue(marketFilter, marketProjection, MarketSort.FIRST_TO_START, maxResults);

            System.out.println("5. Print static marketId, name and runners....\n");
            printMarketCatalogue(marketCatalogueResult.get(0));

            /**
             * ListMarketBook: get list of runners in the market, parameters:
             * marketId:  the market we want to list runners
             *
             */
            System.out.println("6.(listMarketBook) Get volatile info for Market including best 3 exchange prices available...\n");
            String marketIdChosen = marketCatalogueResult.get(0).getMarketId();

            PriceProjection priceProjection = new PriceProjection();
            Set<PriceData> priceData = new HashSet<>();
            priceData.add(PriceData.EX_BEST_OFFERS);
            priceProjection.setPriceData(priceData);

            //In this case we don't need these objects so they are declared null
            OrderProjection orderProjection = null;
            MatchProjection matchProjection = null;
            String currencyCode = null;

            List<String> marketIds = new ArrayList<>();
            marketIds.add(marketIdChosen);

            List<MarketBook> marketBookReturn = rescriptOperations.listMarketBook(
                    marketIds, priceProjection,
                    orderProjection, matchProjection,
                    false, false, null,
                    currencyCode,
                    null, null);

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
                Runner runner = marketBookReturn.get(0).getRunners().get(0);
                selectionId = runner.getSelectionId();
                System.out.println("7. Place a bet below minimum stake to prevent the bet actually " +
                        "being placed for marketId: " + marketIdChosen + " with selectionId: " + selectionId + "...\n\n");

                //You can adjust the size and price value in the "apingdemo.properties" file
                LimitOrder limitOrder = new LimitOrder()
                        .setPersistenceType(PersistenceType.LAPSE)
                        .setPrice(getPrice())
                        .setSize(getSize())
                        .validate();

                PlaceInstruction instruction = new PlaceInstruction()
                        .setHandicap(0)
                        .setSide(Side.BACK)
                        .setOrderType(OrderType.LIMIT)
                        .setLimitOrder(limitOrder)
                        .setSelectionId(selectionId)
                        .validate();

                var instructions = List.of(instruction);

                String customerRef = "1";

                PlaceExecutionReport placeBetResult = rescriptOperations.placeOrders(marketIdChosen, instructions, customerRef);

                // Handling the operation result
                if (placeBetResult.getStatus() == ExecutionReportStatus.SUCCESS) {
                    System.out.println("Your bet has been placed!!");
                    System.out.println(placeBetResult.getInstructionReports());
                } else if (placeBetResult.getStatus() == ExecutionReportStatus.FAILURE) {
                    System.out.println("Your bet has NOT been placed :*( ");
                    System.out.println("The error is: " + placeBetResult.getErrorCode() + ": " + placeBetResult.getErrorCode().getMessage());
                }
            } else {
                System.out.println("Sorry, no runners found\n\n");
            }

        } catch (ApiNgException | JsonProcessingException apiExc) {
            apiExc.printStackTrace();
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

    private void printMarketCatalogue(MarketCatalogue mk) {
        System.out.println("Market Name: " + mk.getMarketName() + "; Id: " + mk.getMarketId() + "\n");
        List<RunnerCatalog> runners = mk.getRunners();
        if (runners != null) {
            for (RunnerCatalog rCat : runners) {
                System.out.println("Runner Name: " + rCat.getRunnerName() + "; Selection Id: " + rCat.getSelectionId() + "\n");
            }
        }
    }

}
