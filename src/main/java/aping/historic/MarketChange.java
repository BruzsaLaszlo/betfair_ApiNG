package aping.historic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historic_market_change")
@NoArgsConstructor
@Getter
public class MarketChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id")
    private Long dbId;

    private String id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "market_definition_db_id")
    private aping.historic.MarketDefinition marketDefinition;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "market_change_db_id")
    private List<aping.historic.RunnerChange> rc;

}