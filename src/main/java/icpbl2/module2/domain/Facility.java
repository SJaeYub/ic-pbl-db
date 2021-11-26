package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Facility {

    @Id
    @GeneratedValue
    private Long facility_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String facility_name;
    private String status;

    protected Facility() {
    }

    public Facility(Cinema cinema, Employee employee, String facility_name, String status) {
        this.cinema = cinema;
        this.employee = employee;
        this.facility_name = facility_name;
        this.status = status;
    }
}
