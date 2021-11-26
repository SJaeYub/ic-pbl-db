package icpbl2.module2.service;

import icpbl2.module2.domain.*;
import icpbl2.module2.repository.CustomerRepository;
import icpbl2.module2.repository.ManageRepository;
import icpbl2.module2.repository.PlayingMovieRepository;
import icpbl2.module2.repository.ReserveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReserveServiceTest {

    //    @Autowired
//    CustomerRepository customerRepository;
//    @Autowired
//    ReserveRepository reserveRepository;
//    @Autowired
//    PlayingMovieRepository playingMovieRepository;
//    @Autowired
//    ManageRepository manageRepository;
    @Autowired
    ReserveService reserveService;
    @Autowired
    ManageService manageService;
    @Autowired
    MovieService movieService;
    @Autowired
    CustomerService customerService;
    @Autowired
    PlayingMovieService playingMovieService;
    @Autowired
    EntityManager em;

    @Test
    public void test_reserve() {
        Customer customer = new Customer("test_id", "test_name", LocalDate.of(1999, 01, 13), MemberStatus.CUSTOMER);
        customerService.join(customer);

        Cinema cinema_s = new Cinema("수원점");
        Cinema cinema_a = new Cinema("안산점");
        manageService.cinemaJoin(cinema_s);
        manageService.cinemaJoin(cinema_a);

        Movie movie = new Movie("test_mv", 20000);
        movieService.join(movie);

        for (int i = 1; i <= 6; i++) {
            Theater theater_n = new Theater(cinema_s, i);
            manageService.theaterJoin(theater_n);
            if (i > 3) {
                theater_n = new Theater(cinema_a, i);
                manageService.theaterJoin(theater_n);
            }
        }

        em.flush();

        Theater theater = em.createQuery("select th from Theater as th " +
                        "where th.cinema.cinema_name=:c_name " +
                        "and th.theater_num=4", Theater.class)
                .setParameter("c_name", cinema_a.getCinema_name())
                .getSingleResult();

        Seat seat = new Seat(cinema_a, theater, 'B', 2);
        Seat seat2 = new Seat(cinema_a, theater, 'B', 3);
        em.persist(seat);
        em.persist(seat2);
        em.flush();

        PlayingMovie playingMovie1 = new PlayingMovie(movie, cinema_s, theater, LocalDateTime.now());
        em.persist(playingMovie1);
        em.flush();

        Customer customer1 = customerService.findOne(customer.getId());
        PlayingMovie playingMovie = playingMovieService.findOne(playingMovie1.getPlayingMovie_id());

        Long reserve_id = reserveService.reserve(customer1.getId(), playingMovie.getPlayingMovie_id(),
                20000, seat.getSeat_id(), seat2.getSeat_id());

        ReservedMovie reservedMovie = reserveService.searchRM(reserve_id);

        System.out.println("reservedMovie.getReservedMovie_id() = " + reservedMovie.getReservedMovie_id());
        System.out.println("reservedMovie.getReservedMovie_name() = " + reservedMovie.getReservedMovie_name());
        System.out.println("reservedMovie.getReservedMovieStatus() = " + reservedMovie.getReservedMovieStatus());
        System.out.println("reservedMovie.getReservedDate() = " + reservedMovie.getReservedDate());
        System.out.println("reservedMovie.getPrice() = " + reservedMovie.getPrice());

        List<ReservedSeat> reservedSeats = reserveService.searchRS(reservedMovie.getReservedMovie_id());
        for (ReservedSeat reservedSeat : reservedSeats) {
            System.out.println("영화관 및 상영관 = " + reservedSeat.getReservedMovie().getPlayingMovie().getCinema().getCinema_name() +
                    " " +
                    reservedSeat.getReservedMovie().getPlayingMovie().getTheater().getTheater_num());
            System.out.println("예약 좌석 = " + reservedSeat.getSeat().getSeat_col() +
                    " " +
                    reservedSeat.getSeat().getSeat_row());
        }
    }

}