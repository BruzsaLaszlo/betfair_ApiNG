package aping.navigation.services;

import aping.enums.Endpoint;
import aping.navigation.dtos.RawObjectDto;
import aping.navigation.entities.*;
import aping.navigation.repositories.NodeRepository;
import aping.navigation.repositories.RawObjectRepository;
import aping.util.HttpUtil;
import aping.util.JsonMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Log4j2
@Component
@RequiredArgsConstructor
public class NavigationDataService {

    final HttpUtil httpUtil;
    final RawObjectRepository rawObjectRepository;
    final NodeRepository rootRepository;
    final JsonMapper jsonMapper;

    @Value("${aping.HORSE_RACING_OFF}")
    private boolean horseRacingOff;

    @Getter
    private final Root root = new Root();


    @Transactional
    public void saveAll() {
        RawObjectDto rootJson = new JsonMapper().readValue(getNavigationDataFromFile(), RawObjectDto.class);
        rawObjectRepository.save(new ModelMapper().map(rootJson, RawObject.class));
    }

    public String getAllDataFromFile() {
        return getDataFromFileUntil(Integer.MAX_VALUE);
    }

    public String getDataFromFileUntil(int deep) {
        StringBuilder sb = new StringBuilder(5_000_000);
        root.getAllData(sb, deep);
        return sb.toString();
    }

    public String getEntitiesCount() {
        return """
                EventTypes: %s
                Events:     %s
                Groups:     %s
                Races:      %s
                Markets:    %s"""
                .formatted(rootRepository.countEventType(), rootRepository.countEvent(), rootRepository.countGroup(), rootRepository.countRace(), rootRepository.countMarket());
    }


    public String getNavigationDataFromFile() {
        try {
            return Files.readString(NAVIGATION_DATA_JSON);
        } catch (IOException exception) {
            throw new IllegalStateException("nincs meg a file: " + NAVIGATION_DATA_JSON);
        }
    }

    @Transactional
    public void updateNavigationData() {
        saveTree(downLoadAndSaveNavigationData());
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");

    private String downLoadAndSaveNavigationData() {
        try {
            String dataJson = httpUtil.sendPostRequest(null, null, Endpoint.NAVIGATION);
            Files.writeString(NAVIGATION_DATA_JSON, dataJson);
            return dataJson;
        } catch (IOException e) {
            log.error("Nem sikerült kiírni a file-ba a NAVIGATION DATA-t", e);
            return null;
        }
    }

    @Transactional
    public void saveWithOnlyRoot() {
        deleteAllFromTables();
        RawObjectDto rootRawObjectDto = jsonMapper.readValue(getNavigationDataFromFile(), RawObjectDto.class);
        mapper(rootRawObjectDto, root, 0);
        rootRepository.saveAll(root.getEventTypes());
    }

    @Transactional//(propagation = Propagation.NEVER)
    public void saveTree(String dataJson) {
        deleteAllFromTables();
        RawObjectDto rootJson = jsonMapper.readValue(dataJson, RawObjectDto.class);
        bejaras(rootJson, root, 0);
    }

    public void deleteAllFromTables() {
        rootRepository.deleteAllFromAllTable();
    }


    private void bejaras(RawObjectDto raw, Node o, int depth) {

        Node nd = null;
        switch (raw.type()) {
            case "EVENT_TYPE" -> {
                if (raw.id().equals("7") && raw.name().equals("Horse Racing") && horseRacingOff)
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
                if (horseRacingOff)
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
        if (!o.getId().equals("root")) {
            rootRepository.save(nd);
            rootRepository.save(o);
        }
        o = nd;

        if (raw.children() != null)
            for (RawObjectDto c : raw.children())
                bejaras(c, o, depth + 1);

    }

    public void mapper(RawObjectDto raw, Node o, int depth) {

        Node nd = null;
        switch (raw.type()) {
            case "EVENT_TYPE" -> {
                if (raw.id().equals("7") && raw.name().equals("Horse Racing") && horseRacingOff)
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
                if (horseRacingOff)
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
