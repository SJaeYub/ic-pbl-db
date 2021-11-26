package icpbl2.module2.service;

import icpbl2.module2.domain.Customer;
import icpbl2.module2.domain.MemberStatus;
import icpbl2.module2.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void 회원가입() throws Exception {
        Customer customer = new Customer("test0", "testName", LocalDate.of(1983, 02, 16), MemberStatus.CUSTOMER);

        Long savedId = customerService.join(customer);

        Assertions.assertEquals(customer, customerRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {

        Customer customer1 = new Customer("test", "testName1", LocalDate.of(1923, 02, 11), MemberStatus.CUSTOMER);
        Customer customer2 = new Customer("test", "testName2", LocalDate.of(1983, 02, 16), MemberStatus.CUSTOMER);

        customerService.join(customer1);
        customerService.join(customer2);

        fail("예외가 발생해야 한다");
    }
}