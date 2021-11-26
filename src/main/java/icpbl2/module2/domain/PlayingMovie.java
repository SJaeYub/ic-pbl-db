package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PlayingMovie {

    @Id
    @GeneratedValue
    private Long playingMovie_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    private String playingMovie_name;
    private LocalDateTime playLocalDateTime;
    private int playingTime;

    protected PlayingMovie() {

    }

    public PlayingMovie(Movie movie, Cinema cinema, Theater theater, LocalDateTime playLocalDateTime) {
        this.movie = movie;
        this.cinema = cinema;
        this.theater = theater;
        this.playingMovie_name = movie.getMovie_name();
        this.playLocalDateTime = playLocalDateTime;
    }
}
