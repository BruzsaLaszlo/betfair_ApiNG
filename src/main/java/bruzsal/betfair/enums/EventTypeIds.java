package bruzsal.betfair.enums;

public enum EventTypeIds {

    SOCCER("1"),
    TENNIS("2"),
    GOLF("3"),
    CRICKET("4"),
    RUGBY_UNION("5"),
    RUGBY_LEAGUE("1477"),
    BOXING("6"),
    HORSE_RACING("7"),
    MOTOR_SPORT("8"),
    ESPORTS("27454571"),
    SPECIAL_BETS("10"),
    VOLLEYBALL("998917"),
    CYCLING("11"),
    GAELIC_GAMES("2152880"),
    SNOOKER("6422"),
    AMERICAN_FOOTBALL("6423"),
    BASEBALL("7511"),
    WINTER_SPORTS("451485"),
    BASKETBALL("7522"),
    ICE_HOCKEY("7524"),
    AUSTRALIAN_RULES("61420"),
    HANDBALL("468328"),
    DARTS("3503"),
    MIXED_MARTIAL_ARTS("26420387"),
    GREYHOUND_RACING("4339"),
    POLITICS("2378961");

    public final String id;

    EventTypeIds(String id) {
        this.id = id;
    }
}
