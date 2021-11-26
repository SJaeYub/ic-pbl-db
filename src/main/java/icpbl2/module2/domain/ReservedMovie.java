package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReservedMovie {

    @Id
    @GeneratedValue
    private Long reservedMovie_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playingMovie_id")
    private PlayingMovie playingMovie;

    private ReservedMovieStatus reservedMovieStatus;
    private String reservedMovie_name;
    private LocalDateTime reservedDate;
    private int price;

    protected ReservedMovie() {

    }

    public ReservedMovie(Customer customer, PlayingMovie playingMovie, int price) {
        this.customer = customer;
        this.movie = playingMovie.getMovie();
        this.playingMovie = playingMovie;
        this.reservedMovie_name = playingMovie.getPlayingMovie_name();
        this.price = price;
        this.reservedDate = playingMovie.getPlayLocalDateTime();
        this.reservedMovieStatus = ReservedMovieStatus.PRE;
    }

    //==예약 생성 메서드==//
    public static ReservedMovie reserveMovie(Customer customer,
                               PlayingMovie playingMovie, int price) {

        ReservedMovie reservedMovie = new ReservedMovie(customer, playingMovie, price);
        playingMovie.getCinema().plusRevenue(price);

        return reservedMovie;
    }
}
