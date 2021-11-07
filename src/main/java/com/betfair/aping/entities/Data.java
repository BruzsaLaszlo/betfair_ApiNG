package com.betfair.aping.entities;

import com.betfair.aping.exceptions.ApiNgException;

public class Data {

    private ApiNgException apiNgException;

    public ApiNgException getApiNgException() {
        return apiNgException;
    }

    public void setApiNgException(ApiNgException aPINGException) {
        apiNgException = aPINGException;
    }

}
