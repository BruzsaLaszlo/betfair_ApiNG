package com.betfair.aping.api;

import com.betfair.aping.FaultData;
import com.betfair.aping.entities.*;
import com.betfair.aping.enums.*;
import com.betfair.aping.exceptions.ApiNgException;
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
    private static final String PRICE_PROJECTION = "priceProjection";
    private static final String MARKET_PROJECTION = "marketProjection";
    private static final String MATCH_PROJECTION = "matchProjection";
    private static final String CURRENCY_CODE = "currencyCode";
    private static final String ORDER_PROJECTION = "orderProjection";
    private static final String PLACED_DATERANGE = "placedDateRange";
    private static final String ORDERBY = "orderBy";
    private static final String DEFAULT_LOCALE = Locale.getDefault().toString();

    /**
     * We needed to override the adapter for the Date class as Betfair's API-NG requires all dates to be serialized in ISO8601 UTC
     * Just formatting the string to the ISO format does not adjust by the timezone on the Date instance during serialization.
     */
    public static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateTypeAdapter()).create();

    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws ApiNgException {

        Map<String, Object> params = new HashMap<>();
        params.put(FILTER, filter);
        params.put(LOCALE, DEFAULT_LOCALE);

        String result = makeRequestBetting(ApiNgOperation.LISTEVENTTYPES.getOperationName(), params);

        return gson.fromJson(result, new TypeToken<List<EventTypeResult>>() {
        }.getType());

    }

    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                           MatchProjection matchProjection, String currencyCode) throws ApiNgException {
        Map<String, Object> params = new HashMap<>();
        params.put(LOCALE, DEFAULT_LOCALE);
        params.put(MARKET_IDS, marketIds);
        params.put(PRICE_PROJECTION, priceProjection);
        params.put(ORDER_PROJECTION, orderProjection);
        params.put(MATCH_PROJECTION, matchProjection);

        String result = makeRequestBetting(ApiNgOperation.LISTMARKETBOOK.getOperationName(), params);

        return gson.fromJson(result, new TypeToken<List<MarketBook>>() {
        }.getType());

    }

    public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
                                                     MarketSort sort, String maxResult) throws ApiNgException {
        Map<String, Object> params = new HashMap<>();
        params.put(LOCALE, DEFAULT_LOCALE);
        params.put(FILTER, filter);
        params.put(SORT, sort);
        params.put(MAX_RESULT, maxResult);
        params.put(MARKET_PROJECTION, marketProjection);

        String result = makeRequestBetting(ApiNgOperation.LISTMARKETCATALOGUE.getOperationName(), params);

        return gson.fromJson(result, new TypeToken<List<MarketCatalogue>>() {
        }.getType());

    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws ApiNgException {

        Map<String, Object> params = new HashMap<>();
        params.put(LOCALE, DEFAULT_LOCALE);
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);

        String result = makeRequestBetting(ApiNgOperation.PLACEORDERS.getOperationName(), params);


        return gson.fromJson(result, PlaceExecutionReport.class);

    }


    protected String makeRequestBetting(String operation, Map<String, Object> params) throws ApiNgException {
        return makeRequest(operation, params, Endpoint.BETTING);
    }

    protected String makeRequestAccount(String operation) throws ApiNgException {
        return makeRequest(operation, null, Endpoint.ACCOUNT);
    }

    private String makeRequest(String operation, Map<String, Object> params, Endpoint endpoint) throws ApiNgException {

        String requestString = params == null ? null : gson.toJson(params);

        String response = null;
        try {

            response = HttpUtil.sendPostRequest(operation, requestString, endpoint);

        } catch (HttpResponseException exception) {

            FaultData fd = Operations.gson.fromJson(exception.getReasonPhrase(), FaultData.class);
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
        return gson.fromJson(response, DeveloperApp.class);
    }

    /**
     * Get all application keys owned by the given developer/vendor
     *
     * @return List<DeveloperApp> A list of application keys owned by the given
     * developer/vendor
     */
    public List<DeveloperApp> getDeveloperAppKeys() throws ApiNgException {
        String response = makeRequestAccount(ApiNgOperation.DEVELOPERAPPKEYS.getOperationName());
        return gson.fromJson(response, new TypeToken<List<DeveloperApp>>() {
        }.getType());
    }

    /**
     * Get available to bet amount.
     *
     * @return Response for retrieving available to bet.
     */
    public AccountFundsResponse getAccountFunds() throws ApiNgException, InaccessibleObjectException {
        String response = makeRequestAccount(ApiNgOperation.ACCOUNTFUNDS.getOperationName());
        return gson.fromJson(response, AccountFundsResponse.class);
    }

    public AccountDetailsResponse getAccountDetails() throws ApiNgException, InaccessibleObjectException {
        String response = makeRequestAccount(ApiNgOperation.ACCOUNTDETAILS.getOperationName());
        return gson.fromJson(response, AccountDetailsResponse.class);
    }

}

