package bruzsal.betfair.enums;

public enum ApiNgOperation {

    HEARTBEAT("heartbeat"),
    LISTEVENTTYPES("listEventTypes"),
    LISTCOMPETITIONS("listCompetitions"),
    LISTTIMERANGES("listTimeRanges"),
    LISTEVENTS("listEvents"),
    LISTMARKETTYPES("listMarketTypes"),
    LISTCOUNTRIES("listCountries"),
    LISTVENUES("listVenues"),
    LISTMARKETCATALOGUE("listMarketCatalogue"),
    LISTMARKETBOOK("listMarketBook"),
    PLACEORDERS("placeOrders"),
    CANCELORDERS("cancelOrders"),
    REPLACEORDERS("replaceOrders"),
    UPDATEORDERS("updateOrders"),
    LISTCLEAREDORDERS("listClearedOrders"),
    LISTCURRENTORDERS("listCurrentOrders"),
    CREATEDEVELOPERAPPKEYS("createDeveloperAppKeys"),
    DEVELOPERAPPKEYS("getDeveloperAppKeys"),
    ACCOUNTFUNDS("getAccountFunds"),
    ACCOUNTDETAILS("getAccountDetails"),
    ACTIVATEAPPLICATIONSUBSCRIPTION("activateApplicationSubscription"),
    VENDORCLIENTID("getVendorClientId"),
    APPLICATIONSUBSCRIPTIONTOKEN("getApplicationSubscriptionToken"),
    CANCELAPPLICATIONSUBSCRIPTION("cancelApplicationSubscription"),
    APPLICATIONSUBSCRIPTIONTOKENS("listApplicationSubscriptionTokens"),
    ACCOUNTSUBSCRIPTIONTOKENS("listAccountSubscriptionTokens"),
    APPLICATIONSUBSCRIPTIONHISTORY("getApplicationSubscriptionHistory");

    private final String name;

    ApiNgOperation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
