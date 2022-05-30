package aping.navigation.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@Getter
public class Group extends Node {

    @OneToMany
    @JoinColumn(name = "group_id")
    private final List<Event> events = new ArrayList<>();

    @OneToMany
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
