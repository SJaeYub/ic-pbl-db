package icpbl2.module2.from;

import icpbl2.module2.domain.MemberStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerForm {

    private String user_id;
    private String name;
    private String password;
    private int year;
    private int month;
    private int day;
    private int status;
}
