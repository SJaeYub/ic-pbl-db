package icpbl2.module2.controller;

import icpbl2.module2.domain.*;
import icpbl2.module2.from.DeleteReserveForm;
import icpbl2.module2.from.MovieForm;
import icpbl2.module2.service.CustomerService;
import icpbl2.module2.service.ManageService;
import icpbl2.module2.service.PlayingMovieService;
import icpbl2.module2.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final PlayingMovieService playingMovieService;
    private final ManageService manageService;
    private final ReserveService reserveService;

    @GetMapping("/customer_info")
    public String showCustomerMenu(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Customer customer, Model model) {
        log.info("customer/customer_info");
        log.info("customer={}", customer.getId());
        model.addAttribute("info", customer);
        return "customer/customer_info";
    }

    @GetMapping("/customer_check")
    public String check(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Customer customer, Model model) {
        log.info("customer/customer_check");
        List<ReservedMovie> reservedMovies = reserveService.list_RM(customer.getId());
        model.addAttribute("list", reservedMovies);

        DeleteReserveForm deleteReserveForm = new DeleteReserveForm();
        model.addAttribute("form", deleteReserveForm);
        return "customer/customer_check";
    }

    @PostMapping("/customer_check")
    public String cancelReserve(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Customer customer, DeleteReserveForm deleteReserveForm, Model model) {
        log.info("cancel Reserve");
        log.info("reservedMovie={}", deleteReserveForm.getId());

        List<Seat> seatList = reserveService.cancelReserve(deleteReserveForm.getId());
        for (Seat seat : seatList) {
            log.info("seat_num={}", seat.getSeat_id());
            log.info("after seat_status={}", seat.getSeat_status());
        }

        log.info("complete cancel reserve={}", deleteReserveForm.getId());
        List<ReservedMovie> reservedMovies = reserveService.list_RM(customer.getId());
        model.addAttribute("list", reservedMovies);

        DeleteReserveForm deleteReserveForm2 = new DeleteReserveForm();
        model.addAttribute("form", deleteReserveForm2);

        return "customer/customer_check";
    }

    @GetMapping("/customer_ticketing")
    public String ticketingMovie(Model model) {
        log.info("customer/customer_ticketing");
        List<PlayingMovie> playingMovieList = playingMovieService.findPM();
        model.addAttribute("list", playingMovieList);
        model.addAttribute("form", new MovieForm());
        return "customer/customer_ticketing";
    }

    @PostMapping("/customer_ticketing")
    public String ticketing(@Valid @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Customer customer, MovieForm movieForm, DeleteReserveForm deleteReserveForm, Model model) {
        log.info("pm_info={}", movieForm);
        log.info("movieForm.getName()={}", movieForm.getName());
        log.info("movieForm.getCinema()={}", movieForm.getCinema());
        log.info("movieForm.getTheater()={}", movieForm.getTheater());
//        log.info("movieForm.getStart_time()={}", movieForm.getStart_time());

        PlayingMovie playingMovie = playingMovieService.findOnePM(movieForm.getName(), movieForm.getCinema(), movieForm.getTheater());
        Seat seatByCNT = manageService.findSeatByCNT(movieForm.getCinema(), movieForm.getTheater(), movieForm.getCol(), movieForm.getRow());

        Long reserveId = reserveService.reserve(customer.getId(), playingMovie.getPlayingMovie_id(), 20000, seatByCNT.getSeat_id());
        log.info("complete reserve={}", reserveId);

        List<ReservedMovie> reservedMovies = reserveService.list_RM(customer.getId());
        model.addAttribute("list", reservedMovies);
        model.addAttribute("form", deleteReserveForm);
        log.info("complete addAttribute={}", reservedMovies);
        return "customer/customer_check";
    }




//    @PostMapping
//    public String check_info() {
//        log.info("check_info");
//        return "customer/customer_info";
//    }


//    @GetMapping
//    public String showForm(@RequestParam String customerId) {
//        log.info("showForm_customer");
//        log.info("userId={}", customerId);
//        return "customer/customer";
//    }
//
//    @GetMapping("/customer_info")
//    public String show_info() {
//        log.info("showInfo_customer");
////        log.info("userId={}", customerId);
//        return "customer/customer_info";
//    }
//
//    @GetMapping("/customer_ticketing")
//    public String show_ticket() {
//        log.info("show_ticket_customer");
//        return "customer/customer_ticketing";
//    }
//
//    @GetMapping("/customer_check")
//    public String show_check() {
//        log.info("show_check_customer");
//        return "customer/customer_check";
//    }


//    @GetMapping("/customers/new")
//    public String createForm(Model model) {
//        log.info("createForm");
//        model.addAttribute("customerForm", new CustomerForm());
//        return "customers/createCustomerForm";
//    }
//
//    @PostMapping("/customers/new")
//    public String create(CustomerForm customerForm) {
//        log.info("create");
//        MemberStatus status;
//        if(customerForm.getStatus() == 1) {
//            status = MemberStatus.CUSTOMER;
//        } else {
//            status = MemberStatus.DB_MANAGER;
//        }
//
//        Customer customer = new Customer(customerForm.getUser_id(), customerForm.getName(),
//                LocalDate.of(customerForm.getYear(), customerForm.getMonth(), customerForm.getDay()), status);
//
//        customerService.join(customer);
//        return "redirect:/";
//    }
//
//    @GetMapping("/customers/list")
//    public String list(Model model) {
//        List<Customer> customers = customerService.findCustomer();
//        model.addAttribute("customers", customers);
//        log.info("list");
//        return "/customers/customerList";
//    }
//
//    @RequestMapping("/customer/customer")
//    public String movieToCustomer() {
//        log.info("moveToCustomer");
//        return "customer/customer";
//    }
//
//    @GetMapping("/customer/customer_info")
//    public String cList(Model model) {
////        customerService.findOne()
//        return "temp";
//    }
}
