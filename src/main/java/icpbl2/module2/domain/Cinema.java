package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Cinema {

    @Id
    @GeneratedValue
    private Long cinema_id;

    private String cinema_name;
    private int revenuePerYear;

    protected Cinema() {

    }

    public Cinema(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public void plusRevenue(int num) {
        this.revenuePerYear += num;
    }
}
