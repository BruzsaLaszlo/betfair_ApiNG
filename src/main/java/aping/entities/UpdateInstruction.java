package aping.entities;

import aping.enums.PersistenceType;

public record UpdateInstruction(

        String betId,
        PersistenceType newPersistenceType

) {
}
