package com.betfair.aping.api;

import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketBook;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.entities.PlaceExecutionReport;
import com.betfair.aping.entities.PlaceInstruction;
import com.betfair.aping.entities.PriceProjection;
import com.betfair.aping.enums.MarketProjection;
import com.betfair.aping.enums.MarketSort;
import com.betfair.aping.enums.MatchProjection;
import com.betfair.aping.enums.OrderProjection;
import com.betfair.aping.exceptions.ApiNgException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public abstract class ApiNgOperations {

	protected static  final String FILTER = "filter";
    protected static  final String LOCALE = "locale";
    protected static  final String SORT = "sort";
    protected static  final String MAX_RESULT = "maxResults";
    protected static  final String MARKET_IDS = "marketIds";
    protected static  final String MARKET_ID = "marketId";
    protected static  final String INSTRUCTIONS = "instructions";
    protected static  final String CUSTOMER_REF = "customerRef";
    protected static  final String MARKET_PROJECTION = "marketProjection";
    protected static  final String PRICE_PROJECTION = "priceProjection";
    protected static  final String MATCH_PROJECTION = "matchProjection";
    protected static  final String ORDER_PROJECTION = "orderProjection";
    protected static  final String DEFAULT_LOCALE = Locale.getDefault().toString();

	public abstract List<EventTypeResult> listEventTypes(MarketFilter filter) throws ApiNgException;

	public abstract List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
						MatchProjection matchProjection, String currencyCode) throws ApiNgException;

    public abstract List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
        MarketSort sort, String maxResult) throws ApiNgException;

	public abstract PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef ) throws ApiNgException;

    protected abstract String makeRequest(String operation, Map<String, Object> params) throws ApiNgException;

}

