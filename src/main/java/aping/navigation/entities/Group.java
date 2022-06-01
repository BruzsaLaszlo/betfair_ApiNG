package aping.navigation.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "node_groups")
@NoArgsConstructor
@Getter
public class Group extends Node {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private final List<Group> groups = new ArrayList<>();

    public Group(int depth, String id, String name) {
        super(depth, id, name);
    }

    @Override
    public String toString() {
        return "Group{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }

}
