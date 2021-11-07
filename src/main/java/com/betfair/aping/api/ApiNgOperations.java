package com.betfair.aping.api;

import com.betfair.aping.entities.*;
import com.betfair.aping.enums.MarketProjection;
import com.betfair.aping.enums.MarketSort;
import com.betfair.aping.enums.MatchProjection;
import com.betfair.aping.enums.OrderProjection;
import com.betfair.aping.exceptions.ApiNgException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public interface ApiNgOperations {

    String FILTER = "filter";
    String LOCALE = "locale";
    String SORT = "sort";
    String MAX_RESULT = "maxResults";
    String MARKET_IDS = "marketIds";
    String MARKET_ID = "marketId";
    String INSTRUCTIONS = "instructions";
    String CUSTOMER_REF = "customerRef";
    String MARKET_PROJECTION = "marketProjection";
    String PRICE_PROJECTION = "priceProjection";
    String MATCH_PROJECTION = "matchProjection";
    String ORDER_PROJECTION = "orderProjection";
    String DEFAULT_LOCALE = Locale.getDefault().toString();

    List<EventTypeResult> listEventTypes(MarketFilter filter) throws ApiNgException;

    List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                    MatchProjection matchProjection, String currencyCode) throws ApiNgException;

    List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
                                              MarketSort sort, String maxResult) throws ApiNgException;

    PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws ApiNgException;

    AccountFundsResponse getAccountFunds() throws ApiNgException;

    AccountDetailsResponse getAccountDetails() throws ApiNgException;

}

