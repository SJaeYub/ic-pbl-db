package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue
    private Long employee_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    private String employee_name;

    @Enumerated(EnumType.STRING)
    private Week work_schedule;
    private int salaryPerYear;

    @Enumerated(EnumType.STRING)
    private Em_rank rank;

    protected Employee() {
    }

    public Employee(Cinema cinema, String employee_name, Week work_schedule, int salaryPerYear, Em_rank rank) {
        this.cinema = cinema;
        this.employee_name = employee_name;
        this.work_schedule = work_schedule;
        this.salaryPerYear = salaryPerYear;
        this.rank = rank;
    }

    public void setSalary(int num) {
        this.salaryPerYear = num;
    }

    public static Employee registerEmployee(Cinema cinema, String employee_name, Week work_schedule, int salaryPerYear, Em_rank rank) {
        Employee employee = new Employee(cinema, employee_name, work_schedule, salaryPerYear, rank);
        return employee;
    }
}
