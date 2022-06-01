alter table node_events
    add constraint fk_node_events_node_events
        foreign key (event_id) references node_events (id),
    add constraint fk_node_events_node_event_types
        foreign key (event_type_id) references node_event_types (id),
    add constraint fk_node_events_node_node_groups
        foreign key (group_id) references node_groups (id);

alter table node_groups
    add constraint fk_node_groups_node_groups
        foreign key (group_id) references node_groups (id),
    add constraint fk_node_groups_node_events
        foreign key (event_id) references node_events (id),
    add constraint fk_node_groups_node_event_types
        foreign key (event_type_id) references node_event_types (id);

alter table node_markets
    add constraint fk_node_markets_node_races
        foreign key (race_id) references node_races (id),
    add constraint fk_node_markets_node_events
        foreign key (event_id) references node_events (id);

alter table node_races
    add constraint fk_node_races_node_event_type
        foreign key (event_type_id) references node_event_types (id);
