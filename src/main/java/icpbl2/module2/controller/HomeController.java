package icpbl2.module2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String Home() {
        log.info("home controller");
        return "home";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("name", "Rooney");
        return "article/list";
    }
}
