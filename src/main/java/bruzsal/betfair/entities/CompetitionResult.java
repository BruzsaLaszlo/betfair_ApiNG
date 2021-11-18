package bruzsal.betfair.entities;

public class CompetitionResult {

    private Competition competition;

    private int marketCount;

    private String competitionRegion;


    public String getCompetitionRegion() {
        return competitionRegion;
    }

    public Competition getCompetition() {
        return competition;
    }

    public int getMarketCount() {
        return marketCount;
    }

    @Override
    public String toString() {
        return String.format("CompetitionResult %n    %s ( %s, id = %s)%n    marketCount: %s",
                competition.getName(),
                competitionRegion,
                competition.getId(),
                marketCount);
    }

}


