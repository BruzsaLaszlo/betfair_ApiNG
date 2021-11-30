package bruzsal.betfair.entities;

public record CompetitionResult(

        Competition competition,

        Integer marketCount,

        String competitionRegion

) {


    @Override
    public String toString() {
        return String.format("CompetitionResult %n    %s ( %s, id = %s)%n    marketCount: %s",
                competition.name(),
                competitionRegion,
                competition.id(),
                marketCount);
    }

}


