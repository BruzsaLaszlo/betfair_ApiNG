package aping.historic;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "historic_runner_change")
@NoArgsConstructor
@Getter
public class RunnerChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id")
    private Long dbId;

    private Integer id;

    private Double ltp;
    private Double hc;

}
