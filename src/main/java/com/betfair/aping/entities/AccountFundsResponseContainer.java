package com.betfair.aping.entities;

import com.betfair.aping.containers.Container;


public class AccountFundsResponseContainer extends Container {
    private AccountFundsResponse result;

    public AccountFundsResponse getResult() {
	return result;
    }

    public void setResult(AccountFundsResponse result) {
	this.result = result;
    }
}
