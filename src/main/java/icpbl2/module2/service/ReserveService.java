package icpbl2.module2.service;

import icpbl2.module2.domain.*;
import icpbl2.module2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static icpbl2.module2.domain.ReservedMovie.reserveMovie;
import static icpbl2.module2.domain.ReservedSeat.reserveSeat;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveService {

    private final CustomerRepository customerRepository;
    private final ReserveRepository reserveRepository;
    private final PlayingMovieRepository playingMovieRepository;
    private final ManageRepository manageRepository;

    /**
     * 예약
     * 테스트 완료
     */
    @Transactional
    public Long reserve(Long customerId, Long playingmovieId, int price, Long... seatId) {
        Customer customer = customerRepository.findOne(customerId);
        PlayingMovie pm = playingMovieRepository.findOne(playingmovieId);

        ReservedMovie reservedMovie = reserveMovie(customer, pm, price);
        reserveRepository.save(reservedMovie);              //예약 저장

        for (Long s_id : seatId) {
            Seat seatById = manageRepository.findSeatById(s_id);
            if(seatById.getSeat_status() == Seat_status.RES) {
                throw new IllegalStateException("이미 예약된 좌석입니다.");
            }
            ReservedSeat reservedSeat = reserveSeat(reservedMovie, seatById, customer);
            manageRepository.reservedSeatSave(reservedSeat);    // 예약 좌석 저장
            reserveRepository.changeStatusSeat(seatById, Seat_status.RES);      //좌석 상태 예약 좌석으로 변경
        }

        return reservedMovie.getReservedMovie_id();
    }

    /**
     * 예약 영화 조회
     * 테스트 완료
     */
    @Transactional
    public ReservedMovie searchRM(Long id) {
        return reserveRepository.findOne(id);
    }

    /**
     * 예약 id로 예약 좌석 조회
     * 테스트 완료
     */
    @Transactional
    public List<ReservedSeat> searchRS(Long r_id) {
        return reserveRepository.findSeatAllByRm_id(r_id);
    }

    /**
     * 예약 취소
     */
}
