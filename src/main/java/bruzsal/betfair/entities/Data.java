package bruzsal.betfair.entities;

import bruzsal.betfair.exceptions.ApiNgException;

public class Data {

    private ApiNgException apiNgException;

    public ApiNgException getApiNgException() {
        return apiNgException;
    }

    public void setApiNgException(ApiNgException aPINGException) {
        apiNgException = aPINGException;
    }

}
