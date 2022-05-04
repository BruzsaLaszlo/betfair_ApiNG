package entities;

import enums.PersistenceType;

public record UpdateInstruction(

        String betId,
        PersistenceType newPersistenceType

) {
}
