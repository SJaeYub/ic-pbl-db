package icpbl2.module2.controller;

import icpbl2.module2.domain.*;
import icpbl2.module2.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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

    @PostConstruct
    @Transactional
    public void addPlayingMovie() {

//        Customer customer = new Customer("testM1, test_manager", )
//        Movie soul = new Movie("귀멸의 칼날", 20000);
//        movieService.join(soul);
////
//        Cinema ansan = new Cinema("안산");
//        manageService.cinemaJoin(ansan);
////
//        Cinema suwon = manageService.findCinemaById(28L);
//        Theater theater_two = new Theater(suwon, 2);
//        manageService.theaterJoin(theater_two);
//
////        PlayingMovie playingMovie = new PlayingMovie(dune, suwon, theater_one, LocalDateTime.now());
//        PlayingMovie playingMovie = new PlayingMovie(soul, ansan, theater_two, LocalDateTime.now());
//        playingMovieService.join(playingMovie);

//        Seat a = new Seat(suwon, theater_two, 'A', 10);
//        manageService.seatSave(a);
//        reserveService.reserve(1L, 30L, 20000, a.getSeat_id());
    }


//    @PostConstruct
//    public void addMAn() {
//        customerService.join(new Customer("testManager",
//                "testMan", LocalDate.of(1999, 2, 18), MemberStatus.DB_MANAGER));
//
//    }

//    @PostMapping
//    public String testParam(@RequestParam String userid,
//                            @RequestParam int userstatus,
//                            Model model) {
//        log.info("userid={} status={}", userid, userstatus);
//        if (userstatus == 0) {
//            Customer admin = customerService.findByNickname(userid);
//            model.addAttribute("customer", admin.getId());
//            return "admin/admin";
//        } else if (userstatus == 1) {
//            Customer customer = customerService.findByNickname(userid);
//            model.addAttribute("customer", customer);
//            return "customer/customer";
//        } else {
//            return "redirect:/";
//        }
//    }


}
