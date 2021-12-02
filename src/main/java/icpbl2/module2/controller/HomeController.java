package icpbl2.module2.controller;

import icpbl2.module2.domain.Customer;
import icpbl2.module2.service.CustomerService;
import icpbl2.module2.service.ManageService;
import icpbl2.module2.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final ManageService manageService;
    @Autowired
    private final MovieService movieService;

//    @PostMapping("/")
//    public String Home(@RequestParam String userid) {
//        log.info("userid={}", userid);
//        return "xxx";
//    }

    @GetMapping
    public String test(Model model) {
        model.addAttribute("name", "Rooney");
        log.info("test");
        return "basic/login";
    }

    @PostMapping
    public String testParam(@RequestParam String userid,
                            @RequestParam int userstatus,
                            Model model) {
        log.info("userid={} status={}", userid, userstatus);
        if (userstatus == 0) {
            Customer admin = customerService.findByNickname(userid);
            model.addAttribute("customer", admin.getId());
            return "admin/admin";
        } else if (userstatus == 1) {
            Customer customer = customerService.findByNickname(userid);
            model.addAttribute("customer", customer);
            return "customer/customer";
        } else {
            return "redirect:/";
        }
    }


}
