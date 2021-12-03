package icpbl2.module2.repository;

import icpbl2.module2.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final EntityManager em;

    public void save(Customer customer) {
        em.persist(customer);
    }

    public Customer findOne(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createQuery("select c from Customer as c", Customer.class)
                .getResultList();
    }

    public List<Customer> findByUserId(String user_id) {
        return em.createQuery("select c from Customer as c where c.user_id =:uid", Customer.class)
                .setParameter("uid", user_id)
                .getResultList();
    }

    public Customer findByNickName(String uid) {   //회원 name을 통한 회원 조회
        return em.createQuery("select m from Customer as m where m.user_id =:uid", Customer.class)
                .setParameter("uid", uid)
                .getSingleResult();
    }
}
