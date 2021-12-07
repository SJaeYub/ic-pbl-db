package icpbl2.module2.from;

import icpbl2.module2.domain.Em_rank;
import icpbl2.module2.domain.Week;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeForm {

    private Long e_id;
    private String name;
    private String cinema;
    private String work_schedule;
    private int salaryPerYear;
    private Em_rank rank;

    public EmployeeForm() {
    }
}
