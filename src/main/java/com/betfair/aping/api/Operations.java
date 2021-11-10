package com.betfair.aping.api;

import com.betfair.aping.entities.*;
import com.betfair.aping.enums.*;
import com.betfair.aping.exceptions.ApiNgException;
import com.betfair.aping.util.FaultData;
import com.betfair.aping.util.HttpUtil;
import com.betfair.aping.util.ISO8601DateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;


public class Operations {

    private static Operations instance;

    private Operations() {
    }

    public static Operations getInstance() {
        if (instance == null)
            instance = new Operations();
        return instance;
    }

    private static final String FILTER = "filter";
    private static final String LOCALE = "locale";
    private static final String SORT = "sort";
    private static final String MAX_RESULT = "maxResults";
    private static final String MARKET_IDS = "marketIds";
    private static final String MARKET_ID = "marketId";
    private static final String INSTRUCTIONS = "instructions";
    private static final String CUSTOMER_REF = "customerRef";
    private static final String PRICEPROJECTION = "priceProjection";
    private static final String MARKETPROJECTION = "marketProjection";
    private static final String MATCHPROJECTION = "matchProjection";
    private static final String CURRENCYCODE = "currencyCode";
    private static final String ORDERPROJECTION = "orderProjection";
    private static final String PLACEDDATERANGE = "placedDateRange";
    private static final String ORDERBY = "orderBy";
    private static final String DEFAULT_LOCALE = Locale.getDefault().toString();

    /**
     * We needed to override the adapter for the Date class as Betfair's API-NG requires all dates to be serialized in ISO8601 UTC
     * Just formatting the string to the ISO format does not adjust by the timezone on the Date instance during serialization.
     */
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateTypeAdapter()).create();


    /**
     * Returns a list of Event Types (i.e. Sports) associated with the markets
     * selected by the MarketFilter
     *
     * @param filter The filter to select desired markets. All markets that match
     *               the criteria in the filter are selected
     * @return
     * @throws ApiNgException
     */
    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        params.put(LOCALE, DEFAULT_LOCALE);
        String result = makeRequestBetting(ApiNgOperation.LISTEVENTTYPES.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<EventTypeResult>>() {
        }.getType());
    }

    /**
     * Returns a list of dynamic data about markets. Dynamic data includes
     * prices, the status of the market, the status of selections, the traded
     * volume, and the status of any orders you have placed in the market
     *
     * @param marketIds       <u>One or more market ids. The number of markets returned
     *                        depends on the amount of data you request via the price
     *                        projection</u>
     * @param priceProjection The projection of price data you want to receive in the
     *                        response
     * @param orderProjection The orders you want to receive in the response
     * @param matchProjection If you ask for orders, specifies the representation of matches
     * @param currencyCode    A Betfair standard currency code. If not specified, the
     *                        default currency code is used
     * @return
     * @throws ApiNgException
     */
    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                           MatchProjection matchProjection, String currencyCode) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(MARKET_IDS, marketIds);
        params.put(PRICEPROJECTION, priceProjection);
        params.put(MATCHPROJECTION, matchProjection);
        params.put(CURRENCYCODE, currencyCode);
        String result = makeRequestBetting(ApiNgOperation.LISTMARKETBOOK.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<MarketBook>>() {
        }.getType());
    }

    /**
     * Returns a list of Countries associated with the markets selected by the
     * MarketFilter
     *
     * @param filter The filter to select desired markets. All markets that match
     *               the criteria in the filter are selected
     * @return
     * @throws ApiNgException
     */
    public List<CountryCodeResult> listCountries(MarketFilter filter) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        String result = makeRequestBetting(ApiNgOperation.LISTCOUNTRIES.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<CountryCodeResult>>() {
        }.getType());
    }

    /**
     * Returns a list of Venues (i.e. Cheltenham, Ascot) associated with the
     * markets selected by the MarketFilter. Currently, only Horse Racing
     * markets are associated with a Venue
     *
     * @param filter
     * @return
     * @throws ApiNgException
     */
    public List<VenueResult> listVenues(MarketFilter filter) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        String result = makeRequestBetting(ApiNgOperation.LISTVENUES.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<VenueResult>>() {
        }.getType());
    }

    /**
     * Returns a list of time ranges in the granularity specified in the request
     * (i.e. 3PM to 4PM, Aug 14th to Aug 15th) associated with the markets
     * selected by the MarketFilter
     *
     * @param filter      <u>The filter to select desired markets. All markets that
     *                    match the criteria in the filter are selected</u>
     * @param granularity <u>The granularity of time periods that correspond to markets
     *                    selected by the market filter</u>
     * @return
     * @throws ApiNgException
     */
    public List<TimeRangeResult> listTimeRanges(MarketFilter filter, TimeGranularity granularity) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        params.put("granularity", granularity);
        String result = makeRequestBetting(ApiNgOperation.LISTTIMERANGES.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<TimeRangeResult>>() {
        }.getType());
    }

    /**
     * Returns a list of information about markets that does not change (or
     * changes very rarely). You use listMarketCatalogue to retrieve the name of
     * the market, the names of selections and other information about markets.
     * Market Data Request Limits apply to requests made to listMarketCatalogue
     *
     * @param filter           <u>The filter to select desired markets. All markets that
     *                         match
     *                         the criteria in the filter are selected</u>
     * @param marketProjection The type and amount of data returned about the market
     * @param sort             The order of the results. Will default to RANK if not passed
     * @param maxResult        <u>limit on the total number of results returned, must be
     *                         greater
     *                         than 0 and less than or equal to 1000</u>
     * @return
     * @throws ApiNgException
     */
    public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection, MarketSort sort,
                                                     String maxResult) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        params.put(SORT, sort);
        params.put(MAX_RESULT, maxResult);
        params.put(MARKETPROJECTION, marketProjection);
        String result = makeRequestBetting(ApiNgOperation.LISTMARKETCATALOGUE.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<MarketCatalogue>>() {
        }.getType());
    }

    /**
     * Returns a list of market types (i.e. MATCH_ODDS, NEXT_GOAL) associated
     * with the markets selected by the MarketFilter. The market types are
     * always the same, regardless of locale
     *
     * @param filter <u>The filter to select desired markets. All markets that
     *               match
     *               the criteria in the filter are selected</u>
     * @return
     * @throws ApiNgException
     */
    public List<MarketTypeResult> listMarketTypes(MarketFilter filter) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        String result = makeRequestBetting(ApiNgOperation.LISTMARKETTYPES.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<MarketTypeResult>>() {
        }.getType());
    }

    /**
     * Place new orders into market. This operation is atomic in that all orders
     * will be placed or none will be placed
     *
     * @param marketId     <u>The market id these orders are to be placed on</u>
     * @param instructions <u>The number of update instructions. The limit of update
     *                     instructions per request is 200</u>
     * @param customerRef  Optional parameter allowing the client to pass a unique string
     *                     (up to 32 chars) that is used to de-dupe mistaken
     *                     re-submissions. CustomerRef can contain: upper/lower chars,
     *                     digits, chars : - . _ + * : ; ~ only
     * @return
     * @throws ApiNgException
     */
    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef)
            throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = makeRequestBetting(ApiNgOperation.PLACEORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<PlaceExecutionReport>>() {
        }.getType());
    }

    /**
     * Cancel all bets OR cancel all bets on a market OR fully or partially
     * cancel particular orders on a market. Only LIMIT orders can be cancelled
     * or partially cancelled once placed
     *
     * @param marketId     <u>The market id these orders are to be placed on</u>
     * @param instructions <u>The number of update instructions. The limit of update
     *                     instructions per request is 60</u>
     * @param customerRef  Optional parameter allowing the client to pass a unique string
     *                     (up to 32 chars) that is used to de-dupe mistaken
     *                     re-submissions
     * @return
     * @throws ApiNgException
     */
    public CancelExecutionReport cancelOrders(String marketId, List<CancelInstruction> instructions, String customerRef)
            throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = makeRequestBetting(ApiNgOperation.CANCELORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<CancelExecutionReport>>() {
        }.getType());
    }

    /**
     * This operation is logically a bulk cancel followed by a bulk place. The
     * cancel is completed first then the new orders are placed. The new orders
     * will be placed atomically in that they will all be placed or none will be
     * placed. In the case where the new orders cannot be placed the
     * cancellations will not be rolled back. See ReplaceInstruction
     *
     * @param marketId     <u>The market id these orders are to be placed on</u>
     * @param instructions <u>The number of update instructions. The limit of update
     *                     instructions per request is 60</u>
     * @param customerRef  Optional parameter allowing the client to pass a unique string
     *                     (up to 32 chars) that is used to de-dupe mistaken
     *                     re-submissions
     * @return
     * @throws ApiNgException
     */
    public ReplaceExecutionReport replaceOrders(String marketId, List<ReplaceInstruction> instructions, String customerRef)
            throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = makeRequestBetting(ApiNgOperation.REPLACEORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<ReplaceExecutionReport>>() {
        }.getType());
    }

    /**
     * Update non-exposure changing fields
     *
     * @param marketId     <u>The market id these orders are to be placed on</u>
     * @param instructions <u>The number of update instructions. The limit of update
     *                     instructions per request is 60</u>
     * @param customerRef  Optional parameter allowing the client to pass a unique string
     *                     (up to 32 chars) that is used to de-dupe mistaken
     *                     re-submissions
     * @return
     * @throws ApiNgException
     */
    public UpdateExecutionReport updateOrders(String marketId, List<UpdateInstruction> instructions, String customerRef)
            throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = makeRequestBetting(ApiNgOperation.UPDATEORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<UpdateExecutionReport>>() {
        }.getType());
    }

    /**
     * Returns a list of your current orders. Optionally you can filter and sort
     * your current orders using the various parameters, setting none of the
     * parameters will return all of your current orders, up to a maximum of
     * 1000 bets, ordered BY_BET and sorted EARLIEST_TO_LATEST. To retrieve more
     * than 1000 orders, you need to make use of the fromRecord and recordCount
     * parameters.
     *
     * @param betIds          Optionally restricts the results to the specified bet IDs
     * @param marketIds       Optionally restricts the results to the specified market IDs
     * @param orderProjection Optionally restricts the results to the specified order
     *                        status.
     * @param placedDateRange Optionally restricts the results to be from/to the specified
     *                        placed date. This date is inclusive, i.e. if an order was
     *                        placed on exactly this date (to the millisecond) then it will
     *                        be included in the results. If the from is later than the to,
     *                        no results will be returned
     * @param orderBy         Specifies how the results will be ordered. If no value is
     *                        passed in, it defaults to BY_BET
     * @param sortDir         Specifies the direction the results will be sorted in. If no
     *                        value is passed in, it defaults to EARLIEST_TO_LATEST
     * @param fromRecord      Specifies the first record that will be returned. Records
     *                        start at index zero, not at index one
     * @param recordCount     Specifies how many records will be returned, from the index
     *                        position 'fromRecord'. Note that there is a page size limit of
     *                        1000. A value of zero indicates that you would like all
     *                        records (including and from 'fromRecord') up to the limit
     * @return
     * @throws ApiNgException
     */
    public CurrentOrderSummaryReport listCurrentOrders(
            Set<String> betIds, Set<String> marketIds, OrderProjection orderProjection,
            TimeRange placedDateRange, OrderBy orderBy, SortDir sortDir, int fromRecord, int recordCount) throws ApiNgException {

        var params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketIds);
        params.put(ORDERPROJECTION, orderProjection);
        params.put(PLACEDDATERANGE, placedDateRange);
        params.put(ORDERBY, orderBy);
        params.put("sortDir", sortDir);
        params.put("fromRecord", fromRecord);
        params.put("recordCount", recordCount);
        String result = makeRequestBetting(ApiNgOperation.UPDATEORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<CurrentOrderSummaryReport>>() {
        }.getType());
    }

    /**
     * Returns a List of bets based on the bet status, ordered by settled date
     *
     * @param betStatus              Restricts the results to the specified status.
     * @param eventTypeIds           Optionally restricts the results to the specified Event Type
     *                               IDs
     * @param eventIds               Optionally restricts the results to the specified Event ID
     * @param marketIds              Optionally restricts the results to the specified market IDs
     * @param runnerIds              Optionally restricts the results to the specified Runners
     * @param betIds                 Optionally restricts the results to the specified bet IDs
     * @param side                   Optionally restricts the results to the specified side
     * @param settledDateRange       Optionally restricts the results to be from/to the specified
     *                               settled date. This date is inclusive, i.e. if an order was
     *                               placed on exactly this date (to the millisecond) then it will
     *                               be included in the results. If the from is later than the to,
     *                               no results will be returned
     * @param groupBy                How to aggregate the lines, if not supplied then the lowest
     *                               level is returned, i.e. bet by bet This is only applicable to
     *                               SETTLED BetStatus.
     * @param includeItemDescription If true then an ItemDescription object is included in the
     *                               response
     * @param locale                 The language used for the itemDescription. If not specified,
     *                               the customer account default is returned
     * @param fromRecord             Specifies the first record that will be returned. Records
     *                               start at index zero
     * @param recordCount            Specifies how many records will be returned, from the index
     *                               position 'fromRecord'. Note that there is a page size limit of
     *                               50. A value of zero indicates that you would like all records
     *                               (including and from 'fromRecord') up to the limit
     * @return
     * @throws ApiNgException
     */
    public ClearedOrderSummaryReport listClearedOrders(
            BetStatus betStatus, Set<String> eventTypeIds, Set<String> eventIds,
            Set<String> marketIds, Set<String> runnerIds, Set<String> betIds, Side side, TimeRange settledDateRange, GroupBy groupBy,
            boolean includeItemDescription, String locale, int fromRecord, int recordCount) throws ApiNgException {

        var params = new HashMap<String, Object>();
        params.put(MARKET_ID, marketIds);
        params.put("eventTypeIds", eventTypeIds);
        params.put("eventIds", eventIds);
        params.put("betStatus", betStatus);
        params.put("runnerIds", runnerIds);
        params.put("side", side);
        params.put("betIds", betIds);
        params.put("fromRecord", fromRecord);
        params.put("recordCount", recordCount);
        params.put("settledDateRange", settledDateRange);
        params.put("groupBy", groupBy);
        params.put("includeItemDescription", includeItemDescription);
        String result = makeRequestBetting(ApiNgOperation.LISTCLEAREDORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<CurrentOrderSummaryReport>>() {
        }.getType());
    }

    /**
     * @param betStatus              Restricts the results to the specified status.
     * @param settledDateRange       Optionally restricts the results to be from/to the specified
     *                               settled date. This date is inclusive, i.e. if an order was
     *                               placed on exactly this date (to the millisecond) then it will
     *                               be included in the results. If the from is later than the to,
     *                               no results will be returned
     * @param groupBy                How to aggregate the lines, if not supplied then the lowest
     *                               level is returned, i.e. bet by bet This is only applicable to
     *                               SETTLED BetStatus.
     * @param includeItemDescription If true then an ItemDescription object is included in the
     *                               response
     * @param fromRecord             Specifies the first record that will be returned. Records
     *                               start at index zero
     * @param recordCount            Specifies how many records will be returned, from the index
     *                               position 'fromRecord'. Note that there is a page size limit of
     *                               50. A value of zero indicates that you would like all records
     *                               (including and from 'fromRecord') up to the limit
     * @return
     * @throws ApiNgException
     */
    public ClearedOrderSummaryReport listClearedOrders(BetStatus betStatus, TimeRange settledDateRange, GroupBy groupBy,
                                                       Boolean includeItemDescription, Integer fromRecord, Integer recordCount) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put("betStatus", betStatus);
        params.put("fromRecord", fromRecord);
        params.put("recordCount", recordCount);
        params.put("settledDateRange", settledDateRange);
        params.put("groupBy", groupBy);
        params.put("includeItemDescription", includeItemDescription);
        String result = makeRequestBetting(ApiNgOperation.LISTCLEAREDORDERS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<ClearedOrderSummaryReport>>() {
        }.getType());
    }

    /**
     * Returns a list of Competitions (i.e., World Cup 2013) associated with the
     * markets selected by the MarketFilter. Currently only Football markets
     * have an associated competition
     *
     * @param filter The filter to select desired markets. All markets that match
     *               the criteria in the filter are selected
     * @return
     * @throws ApiNgException
     */
    public List<CompetitionResult> listCompetitions(MarketFilter filter) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        String result = makeRequestBetting(ApiNgOperation.LISTCOMPETITIONS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<CompetitionResult>>() {
        }.getType());
    }

    /**
     * Returns a list of Events (i.e, Reading vs. Man United) associated with
     * the markets selected by the MarketFilter
     *
     * @param filter The filter to select desired markets. All markets that match
     *               the criteria in the filter are selected
     * @return
     * @throws ApiNgException
     */
    public List<EventResult> listEvents(MarketFilter filter) throws ApiNgException {
        var params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        String result = makeRequestBetting(ApiNgOperation.LISTEVENTS.getOperationName(), params);
        return GSON.fromJson(result, new TypeToken<List<EventTypeResult>>() {
        }.getType());
    }


    protected String makeRequestBetting(String operation, Map<String, Object> params) throws ApiNgException {
        params.put(LOCALE, DEFAULT_LOCALE);
        return makeRequest(operation, params, Endpoint.BETTING);
    }

    protected String makeRequestAccount(String operation) throws ApiNgException {
        return makeRequest(operation, null, Endpoint.ACCOUNT);
    }

    private String makeRequest(String operation, Map<String, Object> params, Endpoint endpoint) throws ApiNgException {

        String requestString = params == null ? null : GSON.toJson(params);

        String response = null;
        try {

            response = HttpUtil.sendPostRequest(operation, requestString, endpoint);

        } catch (HttpResponseException exception) {

            FaultData fd = Operations.GSON.fromJson(exception.getReasonPhrase(), FaultData.class);
            throw fd.getDetail().getAPINGException();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return response;

    }

    /**
     * Create 2 application keys for given user; one active and the other
     * delayed
     *
     * @param appName A Display name for the application.
     * @return DeveloperApp A map of application keys, one marked ACTIVE, and
     * the other DELAYED
     */
    public DeveloperApp createDeveloperAppKeys(String appName) throws ApiNgException {
        String response = makeRequestAccount(ApiNgOperation.DEVELOPERAPPKEYS.getOperationName());
        return GSON.fromJson(response, DeveloperApp.class);
    }

    /**
     * Get all application keys owned by the given developer/vendor
     *
     * @return List<DeveloperApp> A list of application keys owned by the given
     * developer/vendor
     */
    public List<DeveloperApp> getDeveloperAppKeys() throws ApiNgException {
        String response = makeRequestAccount(ApiNgOperation.DEVELOPERAPPKEYS.getOperationName());
        return GSON.fromJson(response, new TypeToken<List<DeveloperApp>>() {
        }.getType());
    }

    /**
     * Get available to bet amount.
     *
     * @return Response for retrieving available to bet.
     */
    public AccountFundsResponse getAccountFunds() throws ApiNgException, InaccessibleObjectException {
        String response = makeRequestAccount(ApiNgOperation.ACCOUNTFUNDS.getOperationName());
        return GSON.fromJson(response, AccountFundsResponse.class);
    }

    public AccountDetailsResponse getAccountDetails() throws ApiNgException, InaccessibleObjectException {
        String response = makeRequestAccount(ApiNgOperation.ACCOUNTDETAILS.getOperationName());
        return GSON.fromJson(response, AccountDetailsResponse.class);
    }

}

