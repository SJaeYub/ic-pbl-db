package icpbl2.module2.controller;

import icpbl2.module2.domain.*;
import icpbl2.module2.from.CustomerForm;
import icpbl2.module2.from.FacilityForm;
import icpbl2.module2.from.LoginForm;
import icpbl2.module2.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;
    private final ManageService manageService;
    private final MovieService movieService;
    private final PlayingMovieService playingMovieService;
    private final ReserveService reserveService;


    @GetMapping("/")
    public String home() {
        log.info("home");
        return "home";
    }

    @GetMapping("/login/customer")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        log.info("loginForm");
        return "login/loginForm";
    }

    @PostMapping("/login/customer")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        log.info("uid={}, password={}", form.getLoginId(), form.getPassword());

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Customer loginMember = customerService.longin(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        if (loginMember.getMemberStatus() == MemberStatus.CUSTOMER) {
            return "customer/customer";
        } else {
            return "admin/admin";
        }

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("logout");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/login/addCustomer")
    public String addCustomerGet(@ModelAttribute CustomerForm customerForm) {
        log.info("addCustomer_g");
        return "customer/addCustomerForm";
    }

    @PostMapping("/login/addCustomer")
    public String addCustomerPost(@ModelAttribute CustomerForm customerForm) {
        log.info("customerForm={}", customerForm.getUser_id());
        log.info("customerForm={}", customerForm.getStatus());
        log.info("customerForm={}", customerForm.getName());

        MemberStatus memberStatus;
        if (customerForm.getStatus() == 1) {
            memberStatus = MemberStatus.CUSTOMER;
        } else if (customerForm.getStatus() == 0) {
            memberStatus = MemberStatus.DB_MANAGER;
        } else {
            memberStatus = MemberStatus.CUSTOMER;
        }

        Customer customer = new Customer(customerForm.getUser_id()
                , customerForm.getName()
                , customerForm.getPassword()
                , LocalDate.of(customerForm.getYear(), customerForm.getMonth(), customerForm.getDay())
                , memberStatus);

        customerService.join(customer);

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String memberId) {
        Cookie cookie = new Cookie(memberId, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

//    @PostConstruct
//    public void initTestData() {
//        Cinema suwon = new Cinema("수원");
//        Cinema ansan = new Cinema("안산");
//
//        manageService.cinemaJoin(suwon);
//        manageService.cinemaJoin(ansan);
//
//        Movie dune = new Movie("Dune", 20000);
//        Movie soul = new Movie("귀멸의 칼날", 20000);
//        Movie ring = new Movie("반지의 제왕", 20000);
//        Movie titanic = new Movie("타이타닉", 20000);
//
//        movieService.join(dune);
//        movieService.join(soul);
//        movieService.join(ring);
//        movieService.join(titanic);
//
//
//        for(int i = 1; i <= 6; i++) {
//            Theater theater_n1 = new Theater(suwon, i);
//            Theater theater_n2 = new Theater(ansan, i);
//            manageService.theaterJoin(theater_n1);
//            manageService.theaterJoin(theater_n2);
//        }
//
//        Character[] temp_col = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//
//        List<Theater> theaterList1 =
//                manageService.searchAllTheaterByCinemaName(suwon.getCinema_name());
//        PlayingMovie playingMovie1 = new PlayingMovie(dune, suwon, theaterList1.get(2), LocalDateTime.of(2021, 12, 11, 14, 00));
//        PlayingMovie playingMovie3 = new PlayingMovie(ring, suwon, theaterList1.get(3), LocalDateTime.of(2021, 12, 8, 20, 30));
//        PlayingMovie playingMovie5 = new PlayingMovie(dune, suwon, theaterList1.get(1), LocalDateTime.of(2021, 12, 7, 14, 00));
//        PlayingMovie playingMovie7 = new PlayingMovie(dune, suwon, theaterList1.get(4), LocalDateTime.of(2021, 12, 17, 8, 00));
//
//        playingMovieService.join(playingMovie1);
//        playingMovieService.join(playingMovie3);
//        playingMovieService.join(playingMovie5);
//        playingMovieService.join(playingMovie7);
//
//
//        for (Theater theater : theaterList1) {
//            for (int i = 0; i < 10; i++) {
//                for (int j = 1; j <= 20; j++) {
//                    Seat seat = new Seat(suwon, theater, temp_col[i], j);
//                    manageService.seatSave(seat);
//                }
//            }
//        }
//
//        List<Theater> theaterList2 = manageService.searchAllTheaterByCinemaName(ansan.getCinema_name());
//
//        PlayingMovie playingMovie2 = new PlayingMovie(soul, ansan, theaterList2.get(1), LocalDateTime.of(2021, 12, 7, 16, 00));
//        PlayingMovie playingMovie4 = new PlayingMovie(titanic, ansan, theaterList2.get(3), LocalDateTime.of(2021, 12, 8, 21, 00));
//        PlayingMovie playingMovie6 = new PlayingMovie(ring, ansan, theaterList2.get(5), LocalDateTime.of(2021, 12, 9, 23, 30));
//        PlayingMovie playingMovie8 = new PlayingMovie(soul, ansan, theaterList2.get(4), LocalDateTime.of(2021, 12, 07, 10, 30));
//
//        playingMovieService.join(playingMovie2);
//        playingMovieService.join(playingMovie4);
//        playingMovieService.join(playingMovie6);
//        playingMovieService.join(playingMovie8);
//
//        for (Theater theater : theaterList2) {
//            for (int i = 0; i < 10; i++) {
//                for (int j = 1; j <= 20; j++) {
//                    Seat seat = new Seat(ansan, theater, temp_col[i], j);
//                    manageService.seatSave(seat);
//                }
//            }
//        }
//        Customer customer = new Customer("testC1", "testName1", "testC1", LocalDate.of(1989, 2, 12), MemberStatus.CUSTOMER);
//        Customer manager = new Customer("testM1", "testName2", "testM1", LocalDate.of(1973, 12, 26), MemberStatus.DB_MANAGER);
//
//        customerService.join(customer);
//        customerService.join(manager);
//
//        Employee test_employee1 = new Employee(suwon, "test_employee1", Week.WEND, 50000000, Em_rank.MANAGER);
//        Employee test_employee2 = new Employee(ansan, "test_employee2", Week.MON, 40000000, Em_rank.STAFF);
//
//        manageService.regiEmployee(test_employee1);
//        manageService.regiEmployee(test_employee2);
//
//        Facility facility1 = new Facility(suwon, test_employee1, "market", "clear");
//        Facility facility2 = new Facility(ansan, test_employee2, "market", "dirty");
//
//        manageService.joinFacil(facility1);
//        manageService.joinFacil(facility2);
//
//    }



}
