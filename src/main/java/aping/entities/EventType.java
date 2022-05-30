package aping.entities;

public record EventType(

        String id,

        String name

) {

    @Override
    public String toString() {
        return name + "(\"" + id + "\")";
    }
}
