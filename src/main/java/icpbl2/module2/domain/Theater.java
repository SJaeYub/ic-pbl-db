package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Theater {

    @Id
    @GeneratedValue
    private Long theater_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    private int theater_num;

    protected Theater() {
    }

    public Theater(Cinema cinema, int theater_num) {
        this.cinema = cinema;
        this.theater_num = theater_num;
    }
}
