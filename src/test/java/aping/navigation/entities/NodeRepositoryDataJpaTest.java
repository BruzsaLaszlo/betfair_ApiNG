package aping.navigation.entities;

import aping.navigation.dtos.RawObjectDto;
import aping.navigation.repositories.NodeRepository;
import aping.util.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("enable_flyway")
//@Sql(value = "classpath:db/migration/V1__node.sql")
class NodeRepositoryDataJpaTest {

    @Autowired
    NodeRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAllFromAllTable();
    }


    @Test
    void saveRoot() throws IOException {
        String jsonData = Files.readString(Path.of("c:\\temp\\NavigationData.json"));
        RawObjectDto rootRawObjectDto = new JsonMapper().readValue(jsonData, RawObjectDto.class);
        Root root = new Root();
        mapper(rootRawObjectDto, root, 0);

        repository.saveAll(root.getEventTypes());

        assertThat(repository.findNodeByName("Soccer").name).isEqualTo("Soccer");
    }


    private void mapper(RawObjectDto raw, Node o, int depth) {

        Node nd = null;
        switch (raw.type()) {
            case "EVENT_TYPE" -> {
                if (raw.id().equals("7") && raw.name().equals("Horse Racing") && true)
                    break;
                nd = new EventType(depth, raw.id(), raw.name());
                ((Root) o).getEventTypes().add((EventType) nd);
            }
            case "GROUP" -> {
                if (raw.name().equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(depth, raw.id(), raw.name());
                add(o, (Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(depth, raw.id(), raw.name(), raw.countryCode());
                add(o, (Event) nd);
            }
            case "RACE" -> {
                if (true)
                    break;
                nd = new Race(depth, raw.id(), raw.name(), raw.venue(), raw.startTime(), raw.raceNumber(), raw.countryCode());
                ((EventType) o).getRaces().add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(depth, raw.id(), raw.name(), raw.marketStartTime(), raw.marketType(), raw.numberOfWinners());
                add(o, (Market) nd);
            }
            default -> throw new IllegalStateException("A root(child) nem lehet más");
        }


        if (nd == null) return;
//        if (!o.id.equals("root")) {
//            rootRepository.save(nd);
//            rootRepository.save(o);
//        }
        o = nd;

        if (raw.children() != null)
            for (RawObjectDto c : raw.children())
                mapper(c, o, depth + 1);

    }

    private void add(Node o, Group g) {
        switch (o) {
            case EventType eventType -> eventType.getGroups().add(g);
            case Group group -> group.getGroups().add(g);
            case Event event -> event.getGroups().add(g);
            default -> throw new IllegalStateException("Az EventType nem lehet más");
        }
    }

    private void add(Node o, Event e) {
        switch (o) {
            case EventType eventType -> eventType.getEvents().add(e);
            case Group group -> group.getEvents().add(e);
            case Event event -> event.getEvents().add(e);
            default -> throw new IllegalStateException("Az Event nem lehet más");
        }
    }

    private void add(Node o, Market m) {
        switch (o) {
            case Event event -> event.getMarkets().add(m);
            case Race race -> race.getMarkets().add(m);
            default -> throw new IllegalStateException("Az Market nem lehet más");
        }
    }
}