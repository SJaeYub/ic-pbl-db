package icpbl2.module2.service;

import icpbl2.module2.domain.*;
import icpbl2.module2.repository.ManageRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ManageServiceTest {

    @Autowired
    ManageRepository manageRepository;
    @Autowired
    ManageService manageService;
    @Autowired
    EntityManager em;

    /**
     * cinema 등록
     */
    @Test
    public void joinCinema() {
        Cinema cinema_s = new Cinema("수원점");

        manageService.cinemaJoin(cinema_s);

        Assertions.assertEquals(cinema_s, manageRepository.findCinemaOne(cinema_s.getCinema_id()));
    }

    @Test
    public void searchCinemaByName() {
        Cinema cinema_s = new Cinema("수원점");

        manageService.cinemaJoin(cinema_s);

        Cinema cinema = manageRepository.findCinemaByName("수원점");

        System.out.println("cinema.getCinema_name() = " + cinema.getCinema_name());
    }

    @Test
    public void joinAndSearchTheater() {
        Cinema cinema_s = new Cinema("수원점");
        Cinema cinema_a = new Cinema("안산점");
        manageService.cinemaJoin(cinema_s);
        manageService.cinemaJoin(cinema_a);

        for (int i = 1; i <= 6; i++) {
            Theater theater_n = new Theater(cinema_s, i);
            manageService.theaterJoin(theater_n);
            if (i > 3) {
                theater_n = new Theater(cinema_a, i);
                manageService.theaterJoin(theater_n);
            }
        }
        em.flush();
        List<Theater> theaterList =
                manageService.searchAllTheaterByCinemaName(cinema_a.getCinema_name());

        for (Theater theater : theaterList) {
            System.out.println("theater.getCinema().getCinema_name() = " + theater.getCinema().getCinema_name());
            System.out.println("theater.getTheater_num() = " + theater.getTheater_num());
        }
    }

    @Test
    public void removeCinema() {
        Cinema cinema_a = getCinema();

        List<Cinema> cinemas = em.createQuery("select ac from Cinema as ac", Cinema.class)
                .getResultList();

        for (Cinema cinema : cinemas) {
            System.out.println("cinema.getCinema_name() = " + cinema.getCinema_name());
        }

        Cinema cinemaRemoved = manageService.findCinemaByName(cinema_a.getCinema_name());
        manageService.cinemaRemove(cinemaRemoved);

        List<Cinema> cinemas1 = em.createQuery("select ac from Cinema as ac", Cinema.class)
                .getResultList();

        for (Cinema cinema : cinemas1) {
            System.out.println("cinema.getCinema_name() = " + cinema.getCinema_name());
        }
    }



    @Test(expected = IllegalStateException.class)
    public void validateObject() {
        Cinema cinema_s = new Cinema("수원점");
        manageService.cinemaJoin(cinema_s);

        Theater theater1 = new Theater(cinema_s, 1);
        Theater theater2 = new Theater(cinema_s, 1);

        manageService.theaterJoin(theater1);
        manageService.theaterJoin(theater2);

        fail("예외가 발생해야 한다");
    }

    @Test
    public void joinAndSearchSeatInTheater() {
        Cinema cinema_s = new Cinema("수원점");
        Cinema cinema_a = new Cinema("안산점");
        manageService.cinemaJoin(cinema_s);
        manageService.cinemaJoin(cinema_a);

        for(int i = 1; i <= 6; i++) {
            Theater theater_n = new Theater(cinema_s, i);
            manageService.theaterJoin(theater_n);
            if(i > 3) {
                theater_n = new Theater(cinema_a, i);
                manageService.theaterJoin(theater_n);
            }
        }
        em.flush();

        Character[] temp_col = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        List<Theater> theaterList =
                manageService.searchAllTheaterByCinemaName(cinema_a.getCinema_name());

        for (Theater theater : theaterList) {
            for (int i = 0; i < 10; i++) {
                for (int j = 1; j <= 20; j++) {
                    Seat seat = new Seat(cinema_s, theater, temp_col[i], j);
                    manageService.seatSave(seat);
                }
            }
        }

        for (Theater theater : theaterList) {
            List<Seat> seats =
                    manageService.searchAllSeatInTheater(cinema_s, theater);
            for (Seat seat : seats) {
                System.out.println(seat.getSeat_col() + " " + seat.getSeat_row());
            }
        }
    }

    @Test
    public void 회원가입_및_조회() {
        String u_id = "test_id";
        String name = "testName";
        LocalDate birth = LocalDate.of(1999, 02, 24);
        MemberStatus memberStatus = MemberStatus.CUSTOMER;

        Long testM = manageService.signUp(u_id, name, birth, memberStatus);

        assertEquals(testM, manageService.searchCustomer("test_id").getId());
    }

    @Test
    public void 직원_등록() {
        Cinema cinem_s = getCinema();
        Employee testEm = Employee.registerEmployee(cinem_s, "test_em", Week.MON, 50000000, Em_rank.STAFF);
        manageService.regiEmployee(testEm);

        Assertions.assertEquals(testEm, manageService.searchOneEmployee(cinem_s, testEm));
    }

    private Cinema getCinema() {
        Cinema cinema_s = new Cinema("수원점");
        Cinema cinema_a = new Cinema("안산점");
        manageService.cinemaJoin(cinema_s);
        manageService.cinemaJoin(cinema_a);

        em.flush();
        return cinema_s;
    }
}