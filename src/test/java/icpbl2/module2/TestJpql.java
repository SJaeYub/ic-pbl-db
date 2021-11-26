package icpbl2.module2;

import icpbl2.module2.domain.Cinema;
import icpbl2.module2.domain.Seat;
import icpbl2.module2.domain.Theater;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestJpql {

    @Autowired
    EntityManager entityManager;

    @Test
    public void jpqlTest() {
        Cinema cinema = new Cinema("수원");
        Theater theater = new Theater(cinema, 1);
        Seat seat = new Seat(cinema, theater, 'A', 1);

        entityManager.persist(cinema);
        entityManager.persist(theater);
        entityManager.persist(seat);

        List<Seat> resultList = entityManager.createQuery("select s from Seat as s where s.seat_col = 'A'", Seat.class)
                .getResultList();

        for (Seat seat1 : resultList) {
            System.out.println("seat1.getSeat_col() = " + seat1.getSeat_col());
            System.out.println("seat1.getSeat_row() = " + seat1.getSeat_row());

        }
    }
}
