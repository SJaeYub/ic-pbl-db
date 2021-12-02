package icpbl2.module2.controller;

import icpbl2.module2.domain.Customer;
import icpbl2.module2.domain.MemberStatus;
import icpbl2.module2.domain.Movie;
import icpbl2.module2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/customer/{customerId}")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @GetMapping
    public String showForm() {
        log.info("showForm_customer");
//        log.info("userId={}", userid);
        return "customer/customer";
    }

    @GetMapping("/customer_info")
    public String show_info() {
        log.info("showInfo_customer");
        return "customer/customer_info";
    }

    @GetMapping("/customer_ticketing")
    public String show_ticket() {
        log.info("show_ticket_customer");
        return "customer/customer_ticketing";
    }

    @GetMapping("/customer_check")
    public String show_check() {
        log.info("show_check_customer");
        return "customer/customer_check";
    }


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
