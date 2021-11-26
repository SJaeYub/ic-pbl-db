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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @GetMapping("/customers/new")
    public String createForm(Model model) {
        log.info("createForm");
        model.addAttribute("customerForm", new CustomerForm());
        return "customers/createCustomerForm";
    }

    @PostMapping("/customers/new")
    public String create(CustomerForm customerForm) {
        log.info("create");
        MemberStatus status;
        if(customerForm.getStatus() == 1) {
            status = MemberStatus.CUSTOMER;
        } else {
            status = MemberStatus.DB_MANAGER;
        }

        Customer customer = new Customer(customerForm.getUser_id(), customerForm.getName(),
                LocalDate.of(customerForm.getYear(), customerForm.getMonth(), customerForm.getDay()), status);

        customerService.join(customer);
        return "redirect:/";
    }

    @GetMapping("/customers/list")
    public String list(Model model) {
        List<Customer> customers = customerService.findCustomer();
        model.addAttribute("customers", customers);
        log.info("list");
        return "/customers/customerList";
    }
}
