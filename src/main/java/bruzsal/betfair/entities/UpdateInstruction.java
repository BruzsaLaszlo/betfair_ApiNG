package bruzsal.betfair.entities;

import bruzsal.betfair.enums.PersistenceType;

public class UpdateInstruction {

    private String betId;
    private PersistenceType newPersistenceType;

    public UpdateInstruction(String betId, PersistenceType newPersistenceType) {
        this.betId = betId;
        this.newPersistenceType = newPersistenceType;
    }


}
