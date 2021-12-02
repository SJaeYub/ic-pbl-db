package icpbl2.module2.service;

import icpbl2.module2.domain.Customer;
import icpbl2.module2.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * 회원 가입
     * 테스트 완료
     * @param customer
     * @return
     */
    @Transactional
    public Long join(Customer customer) {
        validateDuplicateCustomer(customer);
        customerRepository.save(customer);
        return customer.getId();
    }

    /**
     * ID 중복 조회
     * @param customer
     */
    private void validateDuplicateCustomer(Customer customer) {
        List<Customer> findCustomers =
                customerRepository.findByUserId(customer.getUser_id());
        if (!findCustomers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 ID입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Customer findByNickname(String uid) {
        return customerRepository.findByName(uid);
    }

    /**
     * 모든 회원 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<Customer> findCustomer() {
        return customerRepository.findAll();
    }

    /**
     * 키값으로 해당 회원 조회
     * 테스트 완료
     * @param c_id
     * @return
     */
    @Transactional(readOnly = true)
    public Customer findOne(Long c_id) {
        return customerRepository.findOne(c_id);
    }
}
