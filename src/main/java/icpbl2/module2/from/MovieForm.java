package icpbl2.module2.from;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class MovieForm {

    private String name;
    private int view;
    private int ranking;
    private String cinema;
    private int theater;
    private LocalDateTime start_time;
    private Character col;
    private int row;


    public MovieForm() {

    }

    public MovieForm(String name, int view, int rank) {
        this.name = name;
        this.view = view;
        this.ranking = rank;
    }
}
