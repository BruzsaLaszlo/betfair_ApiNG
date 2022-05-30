package aping.historic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historic_root")
@NoArgsConstructor
@Getter
public class Root {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id")
    private Long dbId;

    @Column(name = "operation_type")
    private String op;

    @Column(name = "sequence_token")
    private String clk;

    @Column(name = "published_time")
    private long pt; // in millis since epoch

    /**
     * Changes to market prices, runners or market
     * definition
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "root_db_id")
    private List<MarketChange> mc;


}