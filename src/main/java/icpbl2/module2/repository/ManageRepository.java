package icpbl2.module2.repository;

import icpbl2.module2.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ManageRepository {

    private final EntityManager entityManager;

    public void cinemaSave(Cinema cinema) {
        entityManager.persist(cinema);
    }

    public void cinemaDelete(Cinema cinema) {
        entityManager.remove(cinema);
    }

    public Cinema findCinemaOne(Long id) {
        return entityManager.find(Cinema.class, id);
    }

    public List<Cinema> findByNameForValidate(String name) {
        return entityManager.createQuery("select c from Cinema as c where c.cinema_name=:name", Cinema.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Cinema findCinemaByName(String name) {
        return entityManager.createQuery("select c from Cinema as c where c.cinema_name=:name", Cinema.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Theater> findAllTheater(Cinema cinema) {
        return entityManager.createQuery("select th from Theater as th where th.cinema.cinema_id=:c_id", Theater.class)
                .setParameter("c_id", cinema.getCinema_id())
                .getResultList();
    }

    public void theaterSave(Theater theater) {
        entityManager.persist(theater);
    }

    public void theaterDelete(Theater theater) {
        entityManager.remove(theater);
    }

    public Theater findTheaterOne(Long id) {
        return entityManager.find(Theater.class, id);
    }

    public Long findTheaterByNum(int num) {
        return entityManager.createQuery("select th from Theater as th where th.theater_num=:num", Theater.class)
                .setParameter("num", num)
                .getSingleResult().getTheater_id();
    }

    public void seatSave(Seat seat) {
        entityManager.persist(seat);
    }

    public void reservedSeatSave(ReservedSeat reservedSeat) {
        entityManager.persist(reservedSeat);
    }

    public List<Seat> findAllSeat(Cinema cinema, Theater theater) {
        return entityManager.createQuery("select s from Seat as s where s.theater.theater_id=:target_tid and s.cinema.cinema_id=:target_cid", Seat.class)
                .setParameter("target_tid", theater.getTheater_id())
                .setParameter("target_cid", cinema.getCinema_id())
                .getResultList();
    }

    public Long findSeatByColAndRow(Cinema cinema, Theater theater, Character col, int row) {
        return entityManager.createQuery("select s from Seat as s where s.seat_col=:col and" +
                        " s.seat_row=:row and" +
                        " s.cinema.cinema_id=:c_id and" +
                        " s.theater.theater_id=:t_id", Seat.class)
                .setParameter("col", col)
                .setParameter("row", row)
                .setParameter("c_id", cinema.getCinema_id())
                .setParameter("t_id", theater.getTheater_id())
                .getSingleResult().getSeat_id();
    }

    public Seat findSeatById(Long id) {
        return entityManager.createQuery("select s from Seat as s where s.seat_id=:id", Seat.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public void employeeSave(Employee employee) {
        entityManager.persist(employee);
    }

    public Employee findEmployeeById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    public Employee findOneEmployeeByName(Cinema cinema, String name) {
        return entityManager.createQuery("select e from Employee as e where e.cinema.cinema_id = :c_id " +
                        "and e.employee_name = :name", Employee.class)
                .setParameter("c_id", cinema.getCinema_id())
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Employee> findAllEmployeeInCinema(Cinema cinema) {
        return entityManager.createQuery("select e from Employee as e where e.cinema.cinema_id = :c_id", Employee.class)
                .setParameter("c_id", cinema.getCinema_id())
                .getResultList();
    }

    public void signUpToDb(Customer customer) {
        entityManager.persist(customer);
    }

    public Customer findOneCustomerByUserId(String u_id) {
        return entityManager.createQuery("select c from Customer as c where c.user_id = :u_id", Customer.class)
                .setParameter("u_id", u_id)
                .getSingleResult();
    }


}
