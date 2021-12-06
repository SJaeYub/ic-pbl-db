package icpbl2.module2.repository;

import icpbl2.module2.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

import static icpbl2.module2.domain.ReservedSeat.reserveSeat;

@Repository
@RequiredArgsConstructor
public class ReserveRepository {

    private final EntityManager em;

    public void save(ReservedMovie reservedMovie) {
        em.persist(reservedMovie);
    }

    public ReservedMovie findOne(Long id) {
        return em.find(ReservedMovie.class, id);
    }

    /**
     * 해당 회원의 전체 예약 내역 확인
     */
    public List<ReservedMovie> findAll(Customer customer) {
        return em.createQuery("select rm from ReservedMovie as rm where rm.customer.id=:customer_id", ReservedMovie.class)
                .setParameter("customer_id", customer.getId())
                .getResultList();
    }

    public List<ReservedSeat> findSeatAllByRm_id(Long id) {
        return em.createQuery("select rs from ReservedSeat as rs " +
                        "where rs.reservedMovie.reservedMovie_id=:id", ReservedSeat.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void changeStatusSeat(Seat seat, Seat_status seat_status) {
        seat.changeStatus(seat_status);
    }

    public ReservedSeat findRSById(Long id) {
        return em.find(ReservedSeat.class, id);
    }


}
