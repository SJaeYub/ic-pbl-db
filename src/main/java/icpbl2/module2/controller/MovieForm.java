package icpbl2.module2.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieForm {

    private String name;
    private int view;
    private int ranking;

    public MovieForm() {

    }

    public MovieForm(String name, int view, int rank) {
        this.name = name;
        this.view = view;
        this.ranking = rank;
    }
}
