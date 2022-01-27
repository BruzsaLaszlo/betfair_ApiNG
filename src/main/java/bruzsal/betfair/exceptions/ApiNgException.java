package bruzsal.betfair.exceptions;

import lombok.Getter;

@Getter
public class ApiNgException extends RuntimeException {

    /**
     * TOO_MUCH_DATA
     * The operation requested too much data, exceeding the Market Data Request Limits. You must adjust your request parameters to stay with the documented limits.
     * <p>
     * INVALID_INPUT_DATA
     * The data input is invalid. A specific description is returned via errorDetails as shown below.
     * <p>
     * INVALID_SESSION_INFORMATION
     * The session token hasn't been provided, is invalid or has expired. Login again to create a new session
     * <p>
     * NO_APP_KEY
     * An application key header ('X-Application') has not been provided in the request.
     * <p>
     * NO_SESSION
     * A session token header ('X-Authentication') has not been provided in the request
     * <p>
     * UNEXPECTED_ERROR
     * An unexpected internal error occurred that prevented successful request processing.
     * <p>
     * INVALID_APP_KEY
     * The application key passed is invalid or is not present
     * <p>
     * TOO_MANY_REQUESTS
     * There are too many pending (in-flght) requests e.g. a listMarketBook with Order/Match projections is limited to 3 concurrent requests. The error also applies to:
     * listCurrentOrders, listMarketProfitAndLoss and listClearedOrders if you have 3 or more requests currently in execution.
     * placeOrders, cancelOrders. updateOrders, replaceOrders if the number of transactions submitted exceeds 1000 in a single second.
     * <p>
     * SERVICE_BUSY
     * The service is currently too busy to service this request.
     * <p>
     * TIMEOUT_ERROR
     * The Internal call to downstream service timed out. Please note: If a TIMEOUT error occurs on a placeOrders/replaceOrders request, you should check listCurrentOrders to verify the status of your bets before placing further orders. Please Note: Timeouts will occur after 5 seconds of attempting to process the bet but please allow up to 15 seconds for a timed out order to appear. After this time any unprocessed bets will automatically be Lapsed and no longer be available on the Exchange.
     * <p>
     * REQUEST_SIZE_EXCEEDS_LIMIT	The request exceeds the request size limit. Requests are limited to a total of 250 betId’s/marketId’s (or a combination of both).
     * ACCESS_DENIED	The calling client is not permitted to perform the specific action e.g. they have an App Key restriction in place or attempting to place a bet from a restricted jurisdiction.
     */
    private final String errorCode;
    private final String requestUUID;
    private final String errorDetails;


    public ApiNgException(String requestUUID, String errorCode, String errorDetails) {
        super(errorDetails);
        this.requestUUID = requestUUID;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
    }


    @Override
    public String toString() {
        return "APINGException" + '\n' +
                "   requestUUID='" + requestUUID + '\n' +
                "   errorCode='" + errorCode + '\n' +
                "   errorDetails='" + errorDetails + '\n';
    }
}