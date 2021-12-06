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
//    @Transactional
//    public void addPlayingMovie() {
//
//        Cinema kangnam = manageService.findCinemaById(46L);
//
//        Character[] temp_col = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//
//        List<Theater> theaterList =
//                manageService.searchAllTheaterByCinemaName(kangnam.getCinema_name());
//
//        for (Theater theater : theaterList) {
//            for (int i = 0; i < 10; i++) {
//                for (int j = 1; j <= 20; j++) {
//                    Seat seat = new Seat(kangnam, theater, temp_col[i], j);
//                    manageService.seatSave(seat);
//                }
//            }
//        }
//    }


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
