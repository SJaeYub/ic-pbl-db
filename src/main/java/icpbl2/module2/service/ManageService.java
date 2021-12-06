package icpbl2.module2.service;

import icpbl2.module2.domain.*;
import icpbl2.module2.repository.ManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ManageService {

    private final ManageRepository manageRepository;

    /**
     * cinema 저장
     * 테스트 성공
     *
     * @param cinema
     */
    public Long cinemaJoin(Cinema cinema) {
        validateDuplicate(cinema);
        manageRepository.cinemaSave(cinema);
        return cinema.getCinema_id();
    }

    /**
     * 해당 cinema에 theater 저장
     * 테스트 성공
     *
     * @param theater
     */
    public Long theaterJoin(Theater theater) {
        validateDuplicate(theater);
        manageRepository.theaterSave(theater);
        return theater.getTheater_id();
    }

    /**
     * 이름으로 cinema 찾기
     * 테스트 성공
     *
     * @param c_name
     * @return
     */
    public Cinema findCinemaByName(String c_name) {
        return manageRepository.findCinemaByName(c_name);
    }

    public Cinema findCinemaById(Long c_id) {
        return manageRepository.findCinemaOne(c_id);
    }

    /**
     * 해당 cinema 삭제
     * 테스트 성공
     *
     * @param cinema
     */
    public Long cinemaRemove(Cinema cinema) {
        manageRepository.cinemaDelete(cinema);
        return cinema.getCinema_id();
    }

    /**
     * 해당 cinema의 모든 상영관 정보 확인
     * 테스트 성공
     *
     * @param c_name
     * @return
     */
    public List<Theater> searchAllTheaterByCinemaName(String c_name) {
        Cinema cinema = manageRepository.findCinemaByName(c_name);
        return manageRepository.findAllTheater(cinema);
    }

    /**
     * 해당 영화관의 해당 상영관에 좌석 리턴
     * 테스트 성공
     *
     * @param cinema
     * @param theater
     * @return
     */
    public List<Seat> searchAllSeatInTheater(Cinema cinema, Theater theater) {
        return manageRepository.findAllSeat(cinema, theater);
    }

    public List<Cinema> searchAllCinema() {
        return manageRepository.findAllCinema();
    }

    /**
     * 해당 영화관의 해당 상영관에 좌석 정보 저장
     * 테스트 성공
     *
     * @param seat
     */
    public Long seatSave(Seat seat) {
        manageRepository.seatSave(seat);
        return seat.getSeat_id();
    }

    /**
     * 직원 등록하기
     * 테스트 성공
     * @param employee
     * @return
     */
    public Long regiEmployee(Employee employee) {
        validateDuplicate(employee);
        manageRepository.employeeSave(employee);
        return employee.getEmployee_id();
    }

    /**
     * 해당 시네마 해당 이름의 직원 찾기
     * 테스트 성공
     * @param cinema
     * @param employee
     * @return
     */
    public Employee searchOneEmployee(Cinema cinema, Employee employee) {
        return manageRepository.findOneEmployeeByName(cinema, employee.getEmployee_name());
    }

//    /**
//     * 회원가입
//     * 테스트 성공
//     * @param user_id
//     * @param name
//     * @param birth
//     * @param memberStatus
//     * @return
//     */
//    public Long signUp(String user_id, String name, LocalDate birth, MemberStatus memberStatus) {
//        Customer customer = registerCustomer(user_id, name, birth, memberStatus);
//        manageRepository.signUpToDb(customer);
//        return customer.getId();
//    }

    /**
     * 회원 아이디로 회원 찾기
     * 테스트 성공
     * @param u_id
     * @return
     */
    public Customer searchCustomer(String u_id) {
        return manageRepository.findOneCustomerByUserId(u_id);
    }
    /**
     * 중복값 검사
     * 테스트 성공
     * @param object
     */
    private void validateDuplicate(Object object) {
        if (object instanceof Cinema) {
            Cinema temp = (Cinema) object;
            List<Cinema> byId = manageRepository.findByNameForValidate(temp.getCinema_name());
            if (!byId.isEmpty()) {
                throw new IllegalStateException("이미 등록된 영화관입니다.");
            }
        }
        if(object instanceof Employee) {
            Employee temp = (Employee) object;
            List<Employee> employees = manageRepository.findAllEmployeeInCinema(temp.getCinema());
            for (Employee employee : employees) {
                if(employee.getEmployee_name() == temp.getEmployee_name()) {
                    throw new IllegalStateException("이미 등록된 직원입니다.");
                }
            }
        }
        if (object instanceof Theater) {
            Theater temp = (Theater) object;
            List<Theater> allTheater = manageRepository.findAllTheater(temp.getCinema());
            for (Theater theater : allTheater) {
                if(theater.getTheater_num() == temp.getTheater_num()) {
                    throw new IllegalStateException("해당 영화관에 이미 등록된 상영관입니다.");
                }
            }
        }
    }
}
