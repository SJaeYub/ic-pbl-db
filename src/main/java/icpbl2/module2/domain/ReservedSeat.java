package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ReservedSeat {

    @Id
    @GeneratedValue
    private Long reservedSeat_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservedMovie_id")
    private ReservedMovie reservedMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected ReservedSeat() {

    }

    public ReservedSeat(ReservedMovie reservedMovie, Seat seat, Customer customer) {
        this.reservedMovie = reservedMovie;
        this.seat = seat;
        this.customer = customer;
    }

    public static ReservedSeat reserveSeat(ReservedMovie reservedMovie, Seat seat, Customer customer) {
        ReservedSeat reservedSeat = new ReservedSeat(reservedMovie, seat, customer);
        reservedMovie.getMovie().plusView(1);
        return reservedSeat;
    }

    public void cancelReserve(ReservedMovie reservedMovie) {
        reservedMovie.getMovie().minusView(1);
    }

    public void changeSeatStatus(Seat seat, Seat_status seat_status) {
        seat.changeStatus(seat_status);
    }
}
