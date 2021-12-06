package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String user_id;
    private String name;
    private LocalDate birth;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    protected Customer() {

    }

    public Customer(String user_id, String name, String password, LocalDate birth, MemberStatus memberStatus) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.birth = birth;
        this.memberStatus = memberStatus;
    }

//    //==회원가입 메서드==//
//    public static Customer registerCustomer(String user_id, String name, LocalDate birth, MemberStatus memberStatus) {
//        Customer customer = new Customer(user_id, name, birth, memberStatus);
//        return customer;
//    }
}