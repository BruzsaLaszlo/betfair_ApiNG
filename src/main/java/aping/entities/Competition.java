package aping.entities;

public record Competition(

        String id,
        String name

) {


    @Override
    public String toString() {
        return "Competition {" + '\n' +
                "id='" + id + '\'' + '\n' +
                ", name='" + name + '\'' + '\n' +
                '}';
    }

}
