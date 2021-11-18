package bruzsal.betfair.entities;

import bruzsal.betfair.exceptions.APINGException;

public class Data {

    private APINGException apiNgException;

    public APINGException getApiNgException() {
        return apiNgException;
    }

    public void setApiNgException(APINGException aPINGException) {
        apiNgException = aPINGException;
    }

}
