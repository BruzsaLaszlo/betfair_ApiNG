package com.betfair.aping.api;

import com.betfair.aping.ApiNGDemo;
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
import java.util.*;


public class ApiNgRescriptOperations implements ApiNgOperations {

    private static ApiNgRescriptOperations instance;

    private ApiNgRescriptOperations() {
    }

    public static ApiNgRescriptOperations getInstance() {
        if (instance == null)
            instance = new ApiNgRescriptOperations();
        return instance;
    }

    /**
     * We needed to override the adapter for the Date class as Betfair's API-NG requires all dates to be serialized in ISO8601 UTC
     * Just formatting the string to the ISO format does not adjust by the timezone on the Date instance during serialization.
     */
    public static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateTypeAdapter()).create();

    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws ApiNgException {

        Map<String, Object> params = new HashMap<>();
        params.put(FILTER, filter);
        params.put(LOCALE, DEFAULT_LOCALE);

        String result = makeRequest(ApiNgOperation.LISTEVENTTYPES.getOperationName(), params);

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

        String result = makeRequest(ApiNgOperation.LISTMARKETBOOK.getOperationName(), params);

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

        String result = makeRequest(ApiNgOperation.LISTMARKETCATALOGUE.getOperationName(), params);

        return gson.fromJson(result, new TypeToken<List<MarketCatalogue>>() {
        }.getType());

    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws ApiNgException {

        Map<String, Object> params = new HashMap<>();
        params.put(LOCALE, DEFAULT_LOCALE);
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);

        String result = makeRequest(ApiNgOperation.PLACEORDERS.getOperationName(), params);


        return gson.fromJson(result, PlaceExecutionReport.class);

    }


    private String makeRequest(String operation, Map<String, Object> params) throws ApiNgException {

        //params.put("id", Math.random() * 100);

        String requestString = params == null ? null : gson.toJson(params);

        String response = null;
        try {

            response = HttpUtil.sendPostRequest(operation, requestString);

        } catch (HttpResponseException exception) {

            FaultData fd = ApiNgRescriptOperations.gson.fromJson(exception.getReasonPhrase(), FaultData.class);
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
    public DeveloperApp createDeveloperAppKeys(String appName) {
        return null;
    }

    /**
     * Get all application keys owned by the given developer/vendor
     *
     * @return List<DeveloperApp> A list of application keys owned by the given
     * developer/vendor
     */
//    public List<DeveloperApp> getDeveloperAppKeys() {
//        // TODO Auto-generated method stub
//        return null;
//    }

    /**
     * Get available to bet amount.
     *
     * @return Response for retrieving available to bet.
     */
    public AccountFundsResponse getAccountFunds() throws ApiNgException {
        String response = makeRequest(ApiNgOperation.ACCOUNTFUNDS.getOperationName(), null);
        return gson.fromJson(response, AccountFundsResponse.class);
    }

    public AccountDetailsResponse getAccountDetails() throws ApiNgException {
        String response = makeRequest(ApiNGDemo.getProp().getProperty("ACCOUNT_APING_V1_0") + ApiNgOperation.ACCOUNTDETAILS.getOperationName(), null);
        return gson.fromJson(response, AccountDetailsResponse.class);
    }

}

