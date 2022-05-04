package navigation.entities;

import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "root")
@Getter
public final class Root extends Node {

    private LocalDateTime lastUpdateTime;

    //    @OneToMany
//    @JoinColumn(name = "root_id")
//    @Getter
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
