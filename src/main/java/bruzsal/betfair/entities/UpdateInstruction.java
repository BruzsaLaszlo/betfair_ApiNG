package bruzsal.betfair.entities;

import bruzsal.betfair.enums.PersistenceType;

public record UpdateInstruction(

        String betId,
        PersistenceType newPersistenceType

) {
}
