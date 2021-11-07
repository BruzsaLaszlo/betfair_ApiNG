package com.betfair.aping.api;

import com.betfair.aping.ApiNGDemo;
import com.betfair.aping.entities.*;
import com.betfair.aping.enums.*;
import com.betfair.aping.exceptions.ApiNgException;
import com.betfair.aping.util.HttpUtil;
import com.betfair.aping.util.ISO8601DateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.*;


public class ApiNgRescriptOperations extends ApiNgOperations {

    private static ApiNgRescriptOperations instance = null;

    private ApiNgRescriptOperations() {
    }

    public static ApiNgRescriptOperations getInstance() {
        if (instance == null) {
            instance = new ApiNgRescriptOperations();
        }
        return instance;
    }

    /**
     * We needed to override the adapter for the Date class as Betfair's API-NG requires all dates to be serialized in ISO8601 UTC
     * Just formatting the string to the ISO format does not adjust by the timezone on the Date instance during serialization.
     */
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateTypeAdapter()).create();

    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws ApiNgException {

        Map<String, Object> params = new HashMap<>();
        params.put(FILTER, filter);
        params.put(LOCALE, DEFAULT_LOCALE);

        String result = makeRequest(ApiNgOperation.LISTEVENTTYPES.getOperationName(), params);
        ApiNGDemo.debug(result);

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
        ApiNGDemo.debug(result);

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
        ApiNGDemo.debug(result);

        return gson.fromJson(result, new TypeToken<List<MarketCatalogue>>() {
        }.getType());

    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws ApiNgException {

        Map<String, Object> params = new HashMap<>();
        params.put(LOCALE, DEFAULT_LOCALE);
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);

        String result = makeRequest(ApiNgOperation.PLACORDERS.getOperationName(), params);
        ApiNGDemo.debug(result);

        return gson.fromJson(result, PlaceExecutionReport.class);

    }


    protected String makeRequest(String operation, Map<String, Object> params) throws ApiNgException {

        params.put("id", Math.random());

        String requestString = gson.toJson(params);
        ApiNGDemo.debug(requestString);

        String response = HttpUtil.sendPostRequest(requestString, operation);
        if (response == null) {

            throw new ApiNgException("request " + requestString, "hiba", "response: " + response);
        } else
            return response;

    }

}

