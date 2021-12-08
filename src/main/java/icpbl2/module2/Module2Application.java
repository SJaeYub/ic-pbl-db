package icpbl2.module2;

import icpbl2.module2.domain.Customer;
import icpbl2.module2.domain.MemberStatus;
import icpbl2.module2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor
public class Module2Application {

	public static void main(String[] args) {
		SpringApplication.run(Module2Application.class, args);
	}

}
