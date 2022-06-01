package aping.navigation.entities;

import lombok.Getter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "node_root")
@Getter
public final class Root extends Node {

    private LocalDateTime lastUpdateTime;

    @OneToMany//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "root_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private final List<EventType> eventTypes = new ArrayList<>();

    public Root() {
        super(0, "root", "ROOT");
    }

    @Override
    public String toString() {
        return "Root{" +
               "name='" + name + '\'' +
               ", id='" + id + '\'' +
               '}';
    }

    @PrePersist
    private void setLastUpdateTime() {
        lastUpdateTime = LocalDateTime.now();
    }
}
