package com.betfair.aping.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Race {

    private String id;
    private String name;
    private String venue;
    private Date startTime;
    private String raceNumber;
    private String countryCode;

    private List<Market> markets = new ArrayList<>();

}
