package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue
    private Long seat_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    private Character seat_col;
    private int seat_row;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status")
    private Seat_status seat_status;

    protected Seat() {
    }

    public Seat(Cinema cinema, Theater theater, Character seat_col, int seat_row) {
        this.cinema = cinema;
        this.theater = theater;
        this.seat_col = seat_col;
        this.seat_row = seat_row;
        this.seat_status = Seat_status.POS;            //생성시 좌석 디폴트값 => 사용가능
    }

    public void changeStatus(Seat_status seat_status) {
        this.seat_status = seat_status;                     //예약 현황에 따라 상태변경
    }
}
