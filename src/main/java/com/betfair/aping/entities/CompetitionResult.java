package com.betfair.aping.entities;

public class CompetitionResult {
    public com.betfair.aping.entities.Competition getCompetition() {
        return competition;
    }

    public void setCompetition(com.betfair.aping.entities.Competition competition) {
        this.competition = competition;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }

    private Competition competition;
    private int marketCount;
}


