package com.betfair.aping.entities;

import com.betfair.aping.containers.Container;

public class AccountDetailsResponseContainer extends Container {

    private AccountDetailsResponse result;

    public AccountDetailsResponse getResult() {
	return result;
    }

    public void setResult(AccountDetailsResponse result) {
	this.result = result;
    }
    
}
