package icpbl2.module2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue
    private Long movie_id;

    private String movie_name;
    private int price;
    private int numOfView;

    protected Movie() {

    }

    public Movie(String movie_name, int price) {
        this.movie_name = movie_name;
        this.price = price;
    }

    public void plusView(int num) {
        this.numOfView += num;
    }

    public void minusView(int num) {
        this.numOfView -= num;
    }

}
