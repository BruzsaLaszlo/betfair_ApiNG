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
import java.util.*;


public class Operations {

    private static Operations instance;

    Operations() {
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
     * <p>
     * Please note: Separate requests should be made for OPEN & CLOSED markets.
     * Request that include both OPEN & CLOSED markets will only return those markets that are OPEN.
     * <p>
     * <p>
     * Market Data Request Limits apply to requests made to listMarketBook that include price or order projections.
     * Calls to listMarketBook should be made up to a maximum of 5 times per second to a single marketId.
     * <a href="https://docs.developer.betfair.com/display/1smk3cen4v3lu3yomq5qye0ni/Market+Data+Request+Limits<\a>
     * <p>
     * Best Practice
     * <p>
     * Customers seeking to use listMarketBook to obtain price, volume, unmatched (EXECUTABLE) orders and matched
     * position in a single operation should provide an OrderProjectionof “EXECUTABLE” in their listMarketBook request
     * and receive all unmatched (EXECUTABLE) orders and the aggregated matched volume from all orders irrespective of
     * whether they are partially or fully matched. The level of matched volume aggregation (MatchProjection) requested
     * should be ROLLED_UP_BY_AVG_PRICE or ROLLED_UP_BY_PRICE, the former being preferred. This provides a single call
     * in which you can track prices, traded volume, unmatched orders and your evolving matched position with a
     * reasonably fixed, minimally sized response.
     *
     * @param marketIds One or more market ids. The number of markets returned depends on the amount of data you request via the price projection.
     * @param priceProjection The projection of price data you want to receive in the response.
     * @param orderProjection The orders you want to receive in the response.
     * @param matchProjection If you ask for orders, specifies the representation of matches.
     * @param includeOverallPosition If you ask for orders, returns matches for each selection. Defaults to true if unspecified.
     * @param partitionMatchedByStrategyRef If you ask for orders, returns the breakdown of matches by strategy for each selection. Defaults to false if unspecified.
     * @param customerStrategyRefs If you ask for orders, restricts the results to orders matching any of the specified set of customer defined strategies.
     *                             Also filters which matches by strategy for selections are returned, if partitionMatchedByStrategyRef is true.
     *                             An empty set will be treated as if the parameter has been omitted (or null passed).
     * @param currencyCode A Betfair standard currency code. If not specified, the default currency code is used.
     * @param locale The language used for the response. If not specified, the default is returned.
     * @param matchedSince If you ask for orders, restricts the results to orders that have at least one fragment matched since
     *                     the specified date (all matched fragments of such an order will be returned even if some were matched before the specified date).
     *                     All EXECUTABLE orders will be returned regardless of matched date.
     * @param betIds If you ask for orders, restricts the results to orders with the specified bet IDs. Omitting this
     *               parameter means that all bets will be included in the response. Please note: A maximum of 250 betId's can be provided at a time.
     * @return
     * @throws ApiNgException
     */
    public List<MarketBook> listMarketBook(
            List<String> marketIds,
            PriceProjection priceProjection,
            OrderProjection orderProjection,
            MatchProjection matchProjection,
            boolean includeOverallPosition,
            boolean partitionMatchedByStrategyRef,
            Set<String> customerStrategyRefs,
            String currencyCode,
            Date matchedSince,
            Set<String> betIds)

            throws ApiNgException {

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
     * Returns a list of information about published (ACTIVE/SUSPENDED) markets that does not change (or changes very rarely).
     * You use listMarketCatalogue to retrieve the name of the market, the names of selections and other information about markets.
     * Market Data Request Limits apply to requests made to listMarketCatalogue.
     * <p>
     * Please note: listMarketCatalogue does not return markets that are CLOSED.
     *
     * @param filter           The filter to select desired markets. All markets that match the criteria in the filter are selected.
     * @param marketProjection The type and amount of data returned about the market
     * @param sort             The order of the results. Will default to RANK if not passed.
     *                         RANK is an assigned priority that is determined by our Market Operations team in our back-end system.
     *                         A result's overall rank is derived from the ranking given to the flowing attributes for the result.
     *                         EventType, Competition, StartTime, MarketType, MarketId. For example, EventType is ranked by the most
     *                         popular sports types and marketTypes are ranked in the following order: ODDS ASIAN LINE RANGE
     *                         If all other dimensions of the result are equal, then the results are ranked in MarketId order.
     * @param maxResult        limit on the total number of results returned, must be greater than 0 and less than or equal to 1000
     * @return
     * @throws ApiNgException
     */
    public List<MarketCatalogue> listMarketCatalogue(
            MarketFilter filter,
            Set<MarketProjection> marketProjection,
            MarketSort sort,
            int maxResult)
            throws ApiNgException {

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
        //params.put(LOCALE, DEFAULT_LOCALE);
        return makeRequest(operation, params, Endpoint.BETTING);
    }

    protected String makeRequestAccount(String operation) throws ApiNgException {
        return makeRequest(operation, null, Endpoint.ACCOUNT);
    }

    protected String makeRequestHeartbeat(String operation) throws ApiNgException {
        return makeRequest(operation, null, Endpoint.HEARTBEAT);
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
     * @return List<DeveloperApp> A list of application keys owned by the given developer/vendor
     */
    public List<DeveloperApp> getDeveloperAppKeys() throws ApiNgException {
        String response = makeRequestAccount(ApiNgOperation.DEVELOPERAPPKEYS.getOperationName());
        return GSON.fromJson(response, new TypeToken<List<DeveloperApp>>() {
        }.getType());
    }

    /**
     * Returns the available to bet amount, exposure and commission information.
     *
     * @return Response for retrieving available to bet.
     */
    public AccountFundsResponse getAccountFunds() throws ApiNgException {
        String response = makeRequestAccount(ApiNgOperation.ACCOUNTFUNDS.getOperationName());
        return GSON.fromJson(response, AccountFundsResponse.class);
    }

    /**
     * Returns the details relating your account, including your discount rate and Betfair point balance.
     * <p>
     * Please note: The data returned by getAccountDetails relies on two underlying services.
     * The pointsBalance is returned by a separate service from the other data.
     * As a consequence of this, in the event of a failure to a single underlying service,
     * either the pointsBalance or the remaining data may not be included in the getAccountDetails response.
     * If both services fail, the error UNEXPECTED_ERROR will be returned.
     *
     * @return Response for retrieving account details.
     * @throws ApiNgException Generic exception that is thrown if this operation fails for any reason.
     */
    public AccountDetailsResponse getAccountDetails() throws ApiNgException {
        String response = makeRequestAccount(ApiNgOperation.ACCOUNTDETAILS.getOperationName());
        return GSON.fromJson(response, AccountDetailsResponse.class);
    }


    /**
     * This heartbeat operation is provided to help customers have their positions managed automatically in
     * the event of their API clients losing connectivity with the Betfair API. If a heartbeat request is
     * not received within a prescribed time period, then Betfair will attempt to cancel all 'LIMIT' type bets
     * for the given customer on the given exchange. There is no guarantee that this service will result in all bets
     * being cancelled as there are a number of circumstances where bets are unable to be cancelled.
     * Manual intervention is strongly advised in the event of a loss of connectivity to ensure that positions
     * are correctly managed. If this service becomes unavailable for any reason, then your heartbeat will be
     * unregistered automatically to avoid bets being inadvertently cancelled upon resumption of service.
     * you should manage your position manually until the service is resumed. Heartbeat data may also be lost
     * in the unlikely event of nodes failing within the cluster, which may result in your position not being
     * managed until a subsequent heartbeat request is received.
     *
     * @param preferredTimeoutSeconds Maximum period in seconds that may elapse (without a subsequent heartbeat request),
     *                                before a cancellation request is automatically submitted on your behalf.
     *                                The minimum value is 10, the maximum value permitted is 300. Passing 0 will result in your heartbeat
     *                                being unregistered (or ignored if you have no current heartbeat registered). You will still get an
     *                                actionPerformed value returned when passing 0, so this may be used to determine if any action was performed
     *                                since your last heartbeat, without actually registering a new heartbeat.
     *                                Passing a negative value will result in an error being returned, INVALID_INPUT_DATA.
     *                                Any errors while registering your heartbeat will result in a error being returned, UNEXPECTED_ERROR.
     *                                Passing a value that is less than the minimum timeout will result in your heartbeat adopting the minimum timeout.
     *                                Passing a value that is greater than the maximum timeout will result in your heartbeat adopting the maximum timeout.
     *                                The minimum and maximum timeouts are subject to change, so your client should utilise the returned
     *                                actualTimeoutSeconds to set an appropriate frequency for your subsequent heartbeat requests.
     * @return Response from heartbeat operation
     * @throws ApiNgException Thrown if the operation fails
     */
    public HeartbeatReport heartbeat(int preferredTimeoutSeconds) throws ApiNgException {
        // You should be able to reset the heartbeat by passing a value of actualTimeoutSeconds":0 and then restarting it by setting the required value.
        String response = makeRequestHeartbeat(ApiNgOperation.HEARTBEAT.getOperationName());
        return GSON.fromJson(response, HeartbeatReport.class);
    }

}

